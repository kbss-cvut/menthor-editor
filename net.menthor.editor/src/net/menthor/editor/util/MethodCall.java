package net.menthor.editor.util;

/**
 * ============================================================================================
 * Menthor Editor -- Copyright (c) 2015 
 *
 * This file is part of Menthor Editor. Menthor Editor is based on TinyUML and as so it is 
 * distributed under the same license terms.
 *
 * Menthor Editor is free software; you can redistribute it and/or modify it under the terms 
 * of the GNU General Public License as published by the Free Software Foundation; either 
 * version 2 of the License, or (at your option) any later version.
 *
 * Menthor Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Menthor Editor; 
 * if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, 
 * MA  02110-1301  USA
 * ============================================================================================
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class implements a method call by reflection. It is used to implement
 * pluggable selectors.
 *
 * @author Wei-ju Wu, John Guerson
 */
public class MethodCall {

  private Method method;
  private final Object[] methodParameters;

  /**
   * Constructor.
   * @param theMethod the method to call
   * @param parameters the parameter list
   */
  public MethodCall(final Method theMethod, final Object... parameters) {
    method = theMethod;
    methodParameters = new Object[parameters.length];
    for (int i = 0; i < parameters.length; i++) {
      methodParameters[i] = parameters[i];
    }
  }

  /**
   * Returns the method.
   * @return the method.
   */
  public Method getMethod() { return method; }

  /**
   * Call the method on the specified target.
   * @param target the target object
   */
  public void call(Object target) {
    try {
      method.invoke(target, methodParameters);
    } catch (InvocationTargetException ex) {
      ex.printStackTrace();
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
    }
  }
}
