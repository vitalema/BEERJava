import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
/**
 * @author vitalema and hannantt
 * 
 *         This class represents the current state of the game
 */
@SuppressWarnings("serial")
class GamePanel extends JPanel implements MouseListener, MouseMotionListener,
		Serializable {

	private Image img;
	private Launcher l;

	private InventoryPanel invPanel;
	private boolean mouseIsInsideRegion;
	private SidePanel sidePanel;

	private int timeToWait;
	private WaitViewUpdate waitViewUpdate;

	public GamePanel(Launcher launch, SidePanel sidePane) {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		timeToWait = 2000;
		this.sidePanel = sidePane;
		invPanel = sidePanel.getInvPanel();
		mouseIsInsideRegion = false;
		l = launch;
		this.img = l.getGame().getCurrentView().getCurrentImage().getImage();
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setLayout(new FlowLayout());

	}

	public SidePanel getSidePanel() {
		return this.sidePanel;
	}
	Launcher getLauncher() {
		return l;
	}
	public void drawRegions(Graphics g, View v) {
		if (mouseIsInsideRegion == true) {
			ArrayList<Region> regionList = v.getRegions();
			for (int i = 0; i < regionList.size(); i++) {
				g.setColor(Color.RED);
				g.drawRect(regionList.get(i).getX(), regionList.get(i).getY(),
						regionList.get(i).getWidth(), regionList.get(i)
								.getHeight());
			}
		}
	}

	public void paintComponent(Graphics g) {
		View v = l.getGame().getCurrentView();

		if (l.getGame().isPaused()) {

			v = l.getGame().getPauseView();
		}
		if (l.getGame().getLanguage() == "french") {
			g.drawImage(v.getFrenchImage().getImage(), 0, 0, null);
		} else {
			g.drawImage(v.getCurrentImage().getImage(), 0, 0, null);
		}
		drawRegions(g, v);

	}

	public void updateView(Region currentRegion) {
		l.getGame().changeView(currentRegion.getView());
		sidePanel.updateText();
		mouseIsInsideRegion = false;
		this.repaint();
	}

	public void checkForDynamite(Region currentRegion) {

		if (currentRegion.getRequiredItem().getName().equals("dynamitewithstring")) {
			l.getGame().getCurrentPlayer().getInventory()
					.remove(l.getGame().getCurrentPlayer().getInventory().get(3));

			invPanel.setSelected(l.getGame().getCurrentPlayer().getInventory()
					.get(2));
			invPanel.repaint();
		}
	}

	public void checkItem() {
		Region currentRegion = l.getGame().getCurrentView().getRegions().get(0);
		if (currentRegion.hasItem()
				&& (!this.l.getGame().getCurrentPlayer().getInventory()
						.contains(currentRegion.getItem()))) {
			updateView(currentRegion);
			this.l.getGame().getCurrentPlayer().addItem(currentRegion.getItem());
			this.invPanel.repaint();
		} else {
			if (currentRegion.hasRequiredItem()
					&& (currentRegion.getRequiredItem() == invPanel.selected)) {
				updateView(currentRegion);
				checkForDynamite(currentRegion);
			} else if (currentRegion.hasRequiredItem()
					&& (currentRegion.getRequiredItem() != invPanel.selected)) {
				// do nothing
			} else {
				updateView(currentRegion);

			}
		}
	}

	public void checkRegion(int x, int y) {
		l.getGame().unpauseGame();
		Region currentRegion = l.getGame().getCurrentView().getRegions().get(0);
		if (currentRegion.isInsideRegion(x, y)) {
			checkItem();
		

		}
		checkForWaitView();
	}

	public void setWaitTime(Integer time) {
		timeToWait = time;
	}
//	public void cancelWaitView() {
//		t.cancel();
//		t.purge();
//	}
	
	public WaitViewUpdate getWaitView() {
		return waitViewUpdate;
	}
	public void checkForWaitView() {
		Timer t = new Timer();
			waitViewUpdate = new WaitViewUpdate();
		if (l.getGame().getCurrentView().getRegions().get(0).hasWaitView()) {
			
			t.schedule(waitViewUpdate, timeToWait);

		}

	}

	class WaitViewUpdate extends TimerTask {
		public void run() {
			GamePanel.this.checkRegion(50, 50);

		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
//		if (l.getGame().getCurrentView().getRegions().get(0).getView() != null) {
//		checkRegion(arg0.getX(), arg0.getY());
//		l.getGame().unpauseGame();
//		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {


	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (l.getGame().getCurrentView().getRegions().get(0).getView() != null) {
			checkRegion(arg0.getX(), arg0.getY());
			l.getGame().unpauseGame();
			}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {


	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (l.getGame().getCurrentView().getRegions().get(0).getView() != null) {
		if (l.getGame().getCurrentView().getRegions().get(0)
				.isInsideRegion(e.getX(), e.getY())) {
			mouseIsInsideRegion = true;
			this.repaint();
		} else {
			mouseIsInsideRegion = false;
			this.repaint();
		}
		}


	}
}