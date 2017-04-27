/**
 */
package petrinet.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import petrinet.Arc;
import petrinet.PetrinetPackage;
import petrinet.Transition;
import petrinet.handlers.FireTransitionCommand;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class TransitionImpl extends NodeImpl implements Transition {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PetrinetPackage.Literals.TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void fire() {
		if(isEnabled())
			new FireTransitionCommand(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isEnabled() {
		EList<Arc> listInArcs = this.getIn();
		for (Arc arc : listInArcs){
			if(!arc.isEnabled())
				return false;
		}
		EList<Arc> listOutArcs = this.getOut();
		for (Arc arc : listOutArcs){
			if(!arc.isEnabled())
				return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case PetrinetPackage.TRANSITION___FIRE:
				fire();
				return null;
			case PetrinetPackage.TRANSITION___IS_ENABLED:
				return isEnabled();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TransitionImpl
