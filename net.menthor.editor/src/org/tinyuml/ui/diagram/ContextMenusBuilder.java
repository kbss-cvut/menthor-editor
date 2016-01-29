package org.tinyuml.ui.diagram;

import javax.swing.JPopupMenu;

import org.tinyuml.draw.Connection;
import org.tinyuml.draw.Selection;
import org.tinyuml.umldraw.AssociationElement;
import org.tinyuml.umldraw.shared.UmlConnection;
import org.tinyuml.umldraw.shared.UmlDiagramElement;

import net.menthor.editor.ui.MenthorEditor;
import net.menthor.editor.v2.menu.draw.ConnectionPopupMenu;
import net.menthor.editor.v2.menu.draw.MultiElementPopupMenu;
import net.menthor.editor.v2.menu.draw.NodePopupMenu;

/**
 * This class creates context menus, depending on the specified parameters.
 * 
 * @author Wei-ju Wu, John Guerson
 */
public class ContextMenusBuilder {
	
	private NodePopupMenu singleNodePopup;	
	private ConnectionPopupMenu singleConnectionPopup;	
	private MultiElementPopupMenu multiSelectinoPopup;
	
	public ContextMenusBuilder(DiagramEditor editor)
	{
		singleNodePopup = new NodePopupMenu(MenthorEditor.getFrame());
		singleConnectionPopup = new ConnectionPopupMenu(MenthorEditor.getFrame());		
		multiSelectinoPopup = new MultiElementPopupMenu(MenthorEditor.getFrame());
	}
	
	/**
	 * Created a popup menu for the specified selection.
	 * 
	 * @param selection
	 *            the selection
	 * @return the popup menu
	 */
	public JPopupMenu setContext(Selection selection, double x, double y) {
		if (selection.getElements().size() > 1) {			
			multiSelectinoPopup.setContext(selection.getElements());
			return multiSelectinoPopup;
		} else {
			UmlDiagramElement elem = (UmlDiagramElement) selection.getElement();
			if (elem instanceof Connection) {
				if(elem instanceof AssociationElement){
					// detects when the click is close to the edges...
					double cx1 = ((Connection)elem).getAbsoluteX1();
					double cx2 = ((Connection)elem).getAbsoluteX2();
					double cy1 = ((Connection)elem).getAbsoluteY1();
					double cy2 = ((Connection)elem).getAbsoluteY2();
					double diffx1 = (x-cx1); double diffy1 = (y-cy1);
					double diffx2 = (x-cx2); double diffy2 = (y-cy2);
					if (diffx1<0) diffx1 = diffx1*(-1); if (diffy1<0) diffy1 = diffy1*(-1);
					if (diffx2<0) diffx2 = diffx2*(-1); if (diffy2<0) diffy2 = diffy2*(-1);
					if(diffx1<30 && diffy1<30){	
						singleConnectionPopup.setContext((UmlConnection)elem);//true (isSourceEndPoint)
						return singleConnectionPopup;
					}else if(diffx2<30 && diffy2<30){
						singleConnectionPopup.setContext((UmlConnection)elem);//false (isSourceEndPoint)
						return singleConnectionPopup;
					}
				}				
				singleConnectionPopup.setContext((Connection)elem);
				return singleConnectionPopup;				
			}
			singleNodePopup.setContext(elem);
			return singleNodePopup;
		}
	}
}
