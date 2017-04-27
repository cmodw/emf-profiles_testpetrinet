package petrinet.handlers;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import petrinet.Arc;
import petrinet.Node;
import petrinet.PetrinetFactory;
import petrinet.PetrinetPackage;
import petrinet.Place;
import petrinet.Token;
import petrinet.Transition;

public class FireTransitionCommand extends CompoundCommand {

	public FireTransitionCommand(Transition transition) {
		int in = 0, out = 0;
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(transition);
		EList<Arc> listInArcs = transition.getIn();
		for (Arc arc : listInArcs){
			Node node = arc.getSource();
			if(node instanceof Place){
				for (int i = 0; i < arc.getWeight(); i++) {
					this.append(new RemoveCommand(domain, node, PetrinetPackage.eINSTANCE.getPlace_Tokens(), ((Place) node).getTokens().get(i)));
					in++;
				}
			}
		}
		EList<Arc> listOutArcs = transition.getOut();
		for (Arc arc : listOutArcs){
			Node node = arc.getTarget();
			if(node instanceof Place){
				for (int i = 0; i < arc.getWeight(); i++) {
					Token token = PetrinetFactory.eINSTANCE.createToken();
					this.append(new CreateChildCommand(domain, node, PetrinetPackage.eINSTANCE.getPlace_Tokens(), token, null));
					out++;
				}
			}
		}
		System.out.println("Transition "+transition.getName()+" fired. Tokens in/out: "+in+"/"+out);
		if (domain != null)
			domain.getCommandStack().execute(this);
	}
}
