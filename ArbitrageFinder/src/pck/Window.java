package pck;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.StyledText;

public class Window {

	protected Shell shell;
	private Text odds0_1;
	private Text odds1_0;
	private Text odds2_0;
	private Text odds2_1;
	private Text odds1_2;
	private Text odds0_2;

	private League league;
	private Combo team1;
	private Combo team2;
	private Combo betSiteList;
	private StyledText console;


	public static void main(String[] args) {

		// ReadWriteToFile.main(args);
		Window window = new Window();
		window.open();
		
	}

	public void saveOdds() {
		boolean isCorrectInput = false;
		if (team1.getSelectionIndex() != -1 && team2.getSelectionIndex() != -1 && odds1_0.getText() != ""
				&& odds0_1.getText() != "" && betSiteList.getSelectionIndex() != -1) {
			isCorrectInput = true;
			console.append("\nOdds added to twowaywinnermatch\n");
			readTwoWayWinnerMatch();
		}
		if (team1.getSelectionIndex() != -1 && team2.getSelectionIndex() != -1 && odds2_0.getText() != ""
				&& odds2_1.getText() != "" && odds1_2.getText() != "" && odds0_2.getText() != ""
				&& betSiteList.getSelectionIndex() != -1){
			isCorrectInput = true;
			console.append("\nOdds added to correctmatchscorematch\n");
			readCorrectMatchScoreMatch();
		}
		if(!isCorrectInput){
			console.setText("");
			console.setText("Nincs elég input");
		}
	}

	private void readCorrectMatchScoreMatch() {
		String description = null;
		if(league.getTag().equals("LCK")){
			description = team1.getText()+" vs "+team2.getText() + " - LOL - "+league.getTag()+" Spring Split";	
		}
		else{
			description = team1.getText()+" vs "+team2.getText() + " - LOL - "+league.getTag()+" LCS Spring Split";
		}

		
		double odds20 = isValidOdds(odds2_0.getText());
		double odds21 = isValidOdds(odds2_1.getText());
		double odds02 = isValidOdds(odds0_2.getText());
		double odds12 = isValidOdds(odds1_2.getText());
		String bettingSite = betSiteList.getText();
		
		if(odds20!=0 && odds21!=0 && odds12!=0 && odds02!=0){
			league.addCorrectMatchScoreMatch(description, team1.getText(), team2.getText(), odds20, odds21, odds02, odds12, bettingSite);
		}
		odds2_0.setText("");
		odds2_1.setText("");
		odds0_2.setText("");
		odds1_2.setText("");
	}


	private double isValidOdds(String text){
		double correctOdds = 0;
		String pointed = text.replace(",", ".");
		try{
			correctOdds = Double.parseDouble(pointed);
			if(correctOdds<1 || correctOdds>100){
				console.setText("");
				console.setText("Fucked up odds");
			}
		}
		catch(Exception e){
			console.setText("");
			console.setText("Fucked up odds");
		}
		return correctOdds;
	}

	private void readTwoWayWinnerMatch() {
		String description = null;
		if(league.getTag().equals("LCK")){
			description = team1.getText()+" vs "+team2.getText() + " - LOL - "+league.getTag()+" Spring Split";	
		}
		else{
			description = team1.getText()+" vs "+team2.getText() + " - LOL - "+league.getTag()+" LCS Spring Split";
		}

		double odds10 = isValidOdds(odds1_0.getText());
		double odds01 = isValidOdds(odds0_1.getText());
		String bettingSite = betSiteList.getText();
		
		if(odds10!=0 && odds01!=0){
			league.addTwoWayWinnerMatch(description, team1.getText(), team2.getText(), odds10, odds01, bettingSite);
		}
		odds1_0.setText("");
		odds0_1.setText("");
		
	}

	public void fillLists() {
		betSiteList.removeAll();
		team1.removeAll();
		team2.removeAll();
		for(String act:league.getBetSites()){
			betSiteList.add(act);
		}
		for(String act: league.getTeams()){
			team1.add(act);
			team2.add(act);
		}
	}

	public void changeLeague(String tag) {
		// if user changed the league
		// we save the current league, and load the new one if exists, make a
		// new one if it does not
		console.setText("");
		if(league==null){
			//try to load
			try{
				ObjectInputStream OIS = new ObjectInputStream(
						new FileInputStream("d:\\Eclipse\\Workspace\\ArbitrageFinder\\"+tag+".ser"));
				league = (League) OIS.readObject();
				OIS.close();
			}
			catch(Exception e){
				//there is no saved league -> we create
				System.out.println("error reading" + e.getMessage());
				league = new League(tag);
			}
			fillLists();
		}
		else if (league!=null && league.getTag().equals(tag)){
			//same league remained -> do nothing
		}
		else{
			//user changed the league, we write out the old and try to read the new 
			try {
				league.writeToFile();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				ObjectInputStream OIS = new ObjectInputStream(
						new FileInputStream("d:\\Eclipse\\Workspace\\ArbitrageFinder\\" + tag + ".ser"));
				league = (League) OIS.readObject();
				OIS.close();
			} catch (Exception e) {
				// there is no saved league -> we create
				league = new League(tag);
			}
			fillLists();
		}
	}
	
	public void writeHistoryToConsole(){
		
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	


	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1024, 732);
		shell.setText("0-2");
		shell.setLayout(new GridLayout(13, false));
		new Label(shell, SWT.NONE);
		shell.addListener(SWT.Close, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				if(league!=null){
					try {
						league.writeToFile();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		Button btnEu = new Button(shell, SWT.RADIO);
		btnEu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnEu.getSelection()) changeLeague("EU");
			}
		});

		btnEu.setText("EU");

		Button btnNa = new Button(shell, SWT.RADIO);
		btnNa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnNa.getSelection()) changeLeague("NA");
			}
		});

		btnNa.setText("NA");
		new Label(shell, SWT.NONE);

		Button btnLck = new Button(shell, SWT.RADIO);
		btnLck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnLck.getSelection()) changeLeague("LCK");
			}
		});

		btnLck.setText("LCK");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label lblTeam = new Label(shell, SWT.NONE);
		lblTeam.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTeam.setText("Team1:");

		team1 = new Combo(shell, SWT.NONE);
		GridData gd_euTeam1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_euTeam1.widthHint = 94;
		team1.setLayoutData(gd_euTeam1);
		new Label(shell, SWT.NONE);

		Label lblTeam_1 = new Label(shell, SWT.NONE);
		lblTeam_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTeam_1.setText("Team2:");

		team2 = new Combo(shell, SWT.NONE);
		GridData gd_euTeam2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_euTeam2.widthHint = 103;
		team2.setLayoutData(gd_euTeam2);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label label_27 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label_27 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_27.heightHint = -14;
		label_27.setLayoutData(gd_label_27);
		new Label(shell, SWT.NONE);

		Label label_2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_2.widthHint = 147;
		label_2.setLayoutData(gd_label_2);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label label_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_3.widthHint = 148;
		label_3.setLayoutData(gd_label_3);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label label = new Label(shell, SWT.NONE);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label.setText("1-0");

		odds1_0 = new Text(shell, SWT.BORDER);
		odds1_0.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);

		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label_1.setText("0-1");

		odds0_1 = new Text(shell, SWT.BORDER);
		GridData gd_euOdds0_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_euOdds0_1.widthHint = 88;
		odds0_1.setLayoutData(gd_euOdds0_1);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label label_19 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label_19 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 5, 1);
		gd_label_19.heightHint = 31;
		gd_label_19.widthHint = 554;
		label_19.setLayoutData(gd_label_19);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label label_6 = new Label(shell, SWT.NONE);
		label_6.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label_6.setText("2-0");

		odds2_0 = new Text(shell, SWT.BORDER);
		odds2_0.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);

		Label label_7 = new Label(shell, SWT.NONE);
		label_7.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label_7.setText("0-2");

		odds0_2 = new Text(shell, SWT.BORDER);
		odds0_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label label_8 = new Label(shell, SWT.NONE);
		label_8.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label_8.setText("2-1");

		odds2_1 = new Text(shell, SWT.BORDER);
		odds2_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);

		Label label_9 = new Label(shell, SWT.NONE);
		label_9.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label_9.setText("1-2");

		odds1_2 = new Text(shell, SWT.BORDER);
		odds1_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label lblFogadooldal = new Label(shell, SWT.NONE);
		lblFogadooldal.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblFogadooldal.setText("Fogad\u00F3oldal");

		betSiteList = new Combo(shell, SWT.NONE);
		betSiteList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);

		Button btnEuSaveOdds = new Button(shell, SWT.NONE);
		btnEuSaveOdds.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				saveOdds();
			}
		});
		GridData gd_btnEuSaveOdds = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnEuSaveOdds.widthHint = 99;
		btnEuSaveOdds.setLayoutData(gd_btnEuSaveOdds);
		btnEuSaveOdds.setText("\u00DAj odds");

		Button btnEuArbSearch = new Button(shell, SWT.NONE);
		btnEuArbSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(league!=null){
						league.searchArbitrage(10000, console);
				}
			}
		});
		btnEuArbSearch.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnEuArbSearch.setText("Keress!");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label euResult = new Label(shell, SWT.NONE);
		GridData gd_euResult = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_euResult.heightHint = 48;
		gd_euResult.widthHint = 228;
		euResult.setLayoutData(gd_euResult);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
				
		Label lblEredmny = new Label(shell, SWT.NONE);
		lblEredmny.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblEredmny.setText("Eredm\u00E9ny");

		Label lblBevittAdatok = new Label(shell, SWT.NONE);
		lblBevittAdatok.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblBevittAdatok.setText("Bevitt meccsek");
		new Label(shell, SWT.NONE);

		Button btnShowHistory = new Button(shell, SWT.NONE);
		btnShowHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(league!=null){
					console.setText(" ");
					league.writeMatchesToConsole(console);
				}
			}
		});
		btnShowHistory.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnShowHistory.setText("Mutat");
		
		Button btnClearData = new Button(shell, SWT.NONE);
		btnClearData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(league!=null){
					console.setText(" ");
					league.clearMatches();
					console.setText("Matches has been cleared");
				}
			}
		});
	
		btnClearData.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnClearData.setText("T\u00F6r\u00F6l");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		console = new StyledText(shell, SWT.BORDER);
		GridData gd_console = new GridData(SWT.FILL, SWT.FILL, true, true, 12, 7);
		gd_console.widthHint = 535;
		console.setLayoutData(gd_console);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

	}
}
