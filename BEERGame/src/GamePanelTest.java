import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

public class GamePanelTest {
	View v;
	View v2;
	private Launcher l;
	private GamePanel panel;

	// region is new Region(56, 320, 25, 60, taserItem);
	@Before
	public void setUp() throws Exception{
		this.l = new Launcher();
		l.setGame(l.readGame());
		v = l.getGame().getCurrentView();
		v2 = v.getRegions().get(0).getView();

		SidePanel p = new SidePanel(l);
		InventoryPanel invPanel = new InventoryPanel(l, p);
		panel = new GamePanel(l, p);
	}

	@Test
	public void checkInitialView() {
		assertEquals(v, l.getGame().getCurrentView());
	}

	@Test
	public void checkRegionFalse() {
		panel.checkRegion(850, 850);
		assertEquals(v, l.getGame().getCurrentView());
	}

	@Test
	public void checkRegionChangeFalse() {
		Region currentRegion = l.getGame().getCurrentView().getRegions().get(0);
		panel.checkRegion(currentRegion.getX(), currentRegion.getY());
		assertFalse(v.equals(l.getGame().getCurrentView()));
	}

	@Test
	public void checkRegionChangeTrue() {
		Region currentRegion = l.getGame().getCurrentView().getRegions().get(0);
		panel.checkRegion(currentRegion.getX(), currentRegion.getY());
		assertEquals(v2, l.getGame().getCurrentView());
	}

}
