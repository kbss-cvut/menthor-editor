package net.menthor.editor.v2.ui.notify.model;

import net.menthor.editor.v2.ui.notify.ModelCommand;
import net.menthor.editor.v2.ui.notify.NotificationType;

//TODO: Refator o DeleteGenSetDiagramCommand. Parte dele vem pra cá.
public class DeleteGenSetModelCommand extends ModelCommand {

	private static final long serialVersionUID = 6812364008710754593L;

	public DeleteGenSetModelCommand(){
		super();
		this.notificationType = NotificationType.DELETE;
	}
}
