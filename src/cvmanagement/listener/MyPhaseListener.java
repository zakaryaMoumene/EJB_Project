package cvmanagement.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class MyPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

    @Override
    public void beforePhase(PhaseEvent pe) {
        System.out.println("BEFORE Phase " + pe.getPhaseId());
    }

    @Override
    public void afterPhase(PhaseEvent pe) {
        System.out.println("AFTER Phase " + pe.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

}
