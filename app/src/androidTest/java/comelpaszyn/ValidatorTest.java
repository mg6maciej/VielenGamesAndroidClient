package comelpaszyn;

import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorMove;
import com.elpassion.vielengames.data.kuridor.MoveValidator;
import com.elpassion.vielengames.data.kuridor.PawnPosition;
import com.elpassion.vielengames.data.kuridor.WallPosition;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 16/06/2014.
 */
public class ValidatorTest extends TestCase {
    KuridorGameState kuridorGameState;
    Player player1 = Player.builder().id("1").name("p1").team("team_1").build();
    Player player2 = Player.builder().id("2").name("p2").team("team_2").build();

    public void testWallOutOfRange()
    {
        kuridorGameState = KuridorGameState.initial();
        List<KuridorMove> walls = new ArrayList<KuridorMove>();

        walls.add(KuridorMove.wall("ha1"));
        walls.add(KuridorMove.wall("hh1"));
        walls.add(KuridorMove.wall("hi1"));
        walls.add(KuridorMove.wall("hi9"));

        walls.add(KuridorMove.wall("va1"));
        walls.add(KuridorMove.wall("va5"));
        walls.add(KuridorMove.wall("va9"));
        walls.add(KuridorMove.wall("ve9"));
        walls.add(KuridorMove.wall("vi9"));

        for (KuridorMove move : walls)
            assertFalse(move.getPosition(), MoveValidator.validateMove(kuridorGameState, player1, move));
    }

    public void testWallsInRange()
    {
        kuridorGameState = KuridorGameState.initial();
        List<KuridorMove> walls = new ArrayList<KuridorMove>();

        walls.add(KuridorMove.wall("ha2"));
        walls.add(KuridorMove.wall("hh2"));
        walls.add(KuridorMove.wall("ha9"));
        walls.add(KuridorMove.wall("hh9"));

        walls.add(KuridorMove.wall("vb1"));
        walls.add(KuridorMove.wall("vi1"));
        walls.add(KuridorMove.wall("vb8"));
        walls.add(KuridorMove.wall("vi8"));

        for (KuridorMove move : walls)
            assertTrue(move.getPosition(), MoveValidator.validateMove(kuridorGameState, player1, move));
    }

    public void testBlockingWallMove()
    {
        List<PawnPosition> newPawns = new ArrayList<PawnPosition>(2);
        newPawns.add(PawnPosition.builder().position("e1").team("team_1").build());
        newPawns.add(PawnPosition.builder().position("e9").team("team_2").build());
        kuridorGameState = KuridorGameState.builder().
                pawns(newPawns).
                walls(new ArrayList<WallPosition>()).build();
        kuridorGameState.getWalls().add(WallPosition.builder().position("ha8").build());
        kuridorGameState.getWalls().add(WallPosition.builder().position("hc8").build());
        kuridorGameState.getWalls().add(WallPosition.builder().position("he8").build());
        kuridorGameState.getWalls().add(WallPosition.builder().position("hg8").build());
        kuridorGameState.getWalls().add(WallPosition.builder().position("vi6").build());

        //assertFalse("hh6", MoveValidator.validateMove(kuridorGameState, player1,
        //        KuridorMove.wall("hh6")));
    }

    public void testJumpingOverOpponent()
    {
        List<PawnPosition> newPawns = new ArrayList<PawnPosition>(2);
        newPawns.add(PawnPosition.builder().position("e5").team("team_1").build());
        newPawns.add(PawnPosition.builder().position("e6").team("team_2").build());
        kuridorGameState = KuridorGameState.builder().
                pawns(newPawns).
                walls(new ArrayList<WallPosition>()).build();

        assertTrue("e7", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.pawn("e7")));
    }

    public  void testJumpingOverOpponentWithWall()
    {
        List<PawnPosition> newPawns = new ArrayList<PawnPosition>(2);
        newPawns.add(PawnPosition.builder().position("e5").team("team_1").build());
        newPawns.add(PawnPosition.builder().position("e6").team("team_2").build());
        kuridorGameState = KuridorGameState.builder().
                pawns(newPawns).
                walls(new ArrayList<WallPosition>()).build();
        kuridorGameState.getWalls().add(WallPosition.builder().position("he7").build());

        assertFalse("e7", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.pawn("e7")));
        assertTrue("e7", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.pawn("f6")));
    }

    public void testPawnOutsideBoard()
    {
        List<PawnPosition> newPawns = new ArrayList<PawnPosition>(2);
        newPawns.add(PawnPosition.builder().position("i5").team("team_1").build());
        newPawns.add(PawnPosition.builder().position("a8").team("team_2").build());
        kuridorGameState = KuridorGameState.builder().
                pawns(newPawns).
                walls(new ArrayList<WallPosition>()).build();

        assertFalse("j5", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.pawn("j5")));
    }

    public void testPawnInBoard()
    {
        List<PawnPosition> newPawns = new ArrayList<PawnPosition>(2);
        newPawns.add(PawnPosition.builder().position("i5").team("team_1").build());
        newPawns.add(PawnPosition.builder().position("a8").team("team_2").build());
        kuridorGameState = KuridorGameState.builder().
                pawns(newPawns).
                walls(new ArrayList<WallPosition>()).build();
        kuridorGameState.getWalls().add(WallPosition.builder().position("hc8").build());

        assertTrue("i6", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.pawn("i6")));
        assertTrue("i4", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.pawn("i4")));
        assertTrue("h5", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.pawn("h5")));
    }

    public void testWallBlockingPawnMove()
    {
        List<PawnPosition> newPawns = new ArrayList<PawnPosition>(2);
        newPawns.add(PawnPosition.builder().position("e1").team("team_1").build());
        newPawns.add(PawnPosition.builder().position("e9").team("team_2").build());
        kuridorGameState = KuridorGameState.builder().
                pawns(newPawns).
                walls(new ArrayList<WallPosition>()).build();
        kuridorGameState.getWalls().add(WallPosition.builder().position("he2").build());
        kuridorGameState.getWalls().add(WallPosition.builder().position("he9").build());
        kuridorGameState.getWalls().add(WallPosition.builder().position("ve1").build());

        assertFalse("e2", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.pawn("e2")));
        assertFalse("d1", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.pawn("d1")));
        assertFalse("e8", MoveValidator.validateMove(kuridorGameState, player2,
                KuridorMove.pawn("e8")));
    }

    public void testCrossingWalls()
    {
        List<PawnPosition> newPawns = new ArrayList<PawnPosition>(2);
        newPawns.add(PawnPosition.builder().position("e1").team("team_1").build());
        newPawns.add(PawnPosition.builder().position("e9").team("team_2").build());
        kuridorGameState = KuridorGameState.builder().
                pawns(newPawns).
                walls(new ArrayList<WallPosition>()).build();
        kuridorGameState.getWalls().add(WallPosition.builder().position("hc4").build());

        assertFalse("vd3", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.wall("vd3")));
        assertFalse("hd4", MoveValidator.validateMove(kuridorGameState, player1,
                KuridorMove.wall("hd4")));
    }
}
