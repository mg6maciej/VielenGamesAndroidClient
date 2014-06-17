package comelpaszyn;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorMove;
import com.elpassion.vielengames.data.kuridor.PawnPosition;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 16/06/2014.
 */
public class ValidatorTest extends TestCase {
    KuridorGameState kuridorGameState;
    List<KuridorMove> moves;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        kuridorGameState = KuridorGameState.initial();
        moves = new ArrayList<KuridorMove>();

    }

    public void testWallMoveValidation()
    {

    }

    public void testPawnMoveValidation()
    {

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
