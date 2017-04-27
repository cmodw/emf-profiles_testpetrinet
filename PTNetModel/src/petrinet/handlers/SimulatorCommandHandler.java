package petrinet.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.util.EList;

import petrinet.Arc;
import petrinet.Node;
import petrinet.PetriNet;
import petrinet.Place;
import petrinet.Transition;

public class SimulatorCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object object = getTransitionOrPetriNet(event.getApplicationContext());
		if(object instanceof Transition)
			((Transition)object).fire();
		if(object instanceof PetriNet)
			fireAll((PetriNet) object);
		return null;
	}

	@Override
	public void setEnabled(Object context) {
		//Object object = getTransitionOrPetriNet(context);
		setBaseEnabled(true);//object != null);
	}

	private Object getTransitionOrPetriNet(Object context) {
		if (context instanceof IEvaluationContext) {
			IEvaluationContext evaluationContext = (IEvaluationContext) context;
			Object object = evaluationContext.getDefaultVariable();
			if (object instanceof List) {
				@SuppressWarnings("rawtypes")
				List list = (List) object;
				if (list.size() == 1) {
					object = list.get(0);
					if (object instanceof Transition) {
						return (Transition) object;
					}
					if (object instanceof PetriNet) {
						return (PetriNet) object;
					}
				}
			}
		}
		return null;
	}
	
	private void fireAll(PetriNet petrinet) {
		boolean retry = true;
		while (retry) {
			retry = false;
			for(Object object : petrinet.getNodes()) {
				if(object instanceof Transition) {
					while (((Transition)object).isEnabled()) {
						((Transition)object).fire();
						retry = true;
					} //TODO endless loop on inhibitor arcs > always enabled if no token
				}
			}
		}
	}
	//static
	/*
	static private boolean isEnabled(Transition transition) {
		EList<Arc> listInArcs = transition.getIn();
		for (Arc arc : listInArcs){
			Node node = arc.getSource();
			if(node instanceof Place){
				if(((Place) node).getTokens().size()<1) {
					return false;
				}
			}
		}

		return true;
	}*/
	//static
	/*static private void fire(Transition transition) {
			new FireTransitionCommand(transition);
		EList<Arc> listInArcs = transition.getIn();
		for (Arc arc : listInArcs){
			Node node = arc.getSource();
			if(node instanceof Place){
				((Place) node).getTokens().remove(0);
			}
		}
		EList<Arc> listOutArcs = transition.getOut();
		for (Arc arc : listOutArcs){
			Node node = arc.getTarget();
			if(node instanceof Place){
				Token token = PetrinetFactory.eINSTANCE.createToken();
				((Place) node).getTokens().add(token);
			}
		}
		//EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(transition);
		//  if (domain != null)
			//try {
				//domain.getCommandStack().execute((Command) getCmd());//getCla().newInstance());//getCommand());
			
	}*/
}
