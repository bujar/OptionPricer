/**
 * OOAD Final Project Team 4
 * Member: Hang Li, Steven Capetta, Bujar Tagani
 * This contains main method and supposed to provide the user interface.
 */
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.widgets.Canvas;

import javax.swing.JOptionPane;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.custom.TableCursor;



public class Calculation {

	protected Shell shell;
	private Table staticTable;
	private Text txtStockPrice;
	private Text txtStrikePrice;
	private Text txtVolatility;
	private Text txtRiskFreeInt;
	private Text txtYear;
	private Text txtIntervals;
	private Text maxPrice;
	private Spinner spinTimeInterval;
	private Spinner priceIntervals;
	private Spinner spinTrials;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Calculation window = new Calculation();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
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
		/**Variables**/
		String options[] = { "American Call", "American Put", "European Call", "European Put", "Asian Call", "Asian Put" };
		
		
		shell = new Shell();
		shell.setSize(418, 325);
		shell.setText("OPTION PRICE SOFTWARE");
		Group grpChooseOptions = new Group(shell, SWT.NONE);
		grpChooseOptions.setText("Choose Options & Algorithms");
		grpChooseOptions.setBounds(10, 10, 398, 158);
		
		final Combo cOption = new Combo(grpChooseOptions, SWT.NONE);
		cOption.setItems(options);
		cOption.setBounds(149, 10, 207, 22);
		Label lblChooseOptions = new Label(grpChooseOptions, SWT.NONE);
		lblChooseOptions.setBounds(10, 14, 90, 22);
		lblChooseOptions.setText("Choose Options");
		
		Label lblChooseAlgorithms = new Label(grpChooseOptions, SWT.NONE);
		lblChooseAlgorithms.setBounds(10, 68, 107, 18);
		lblChooseAlgorithms.setText("Choose Algorithms");
		
		 final Combo cAlgorithm = new Combo(grpChooseOptions, SWT.NONE);
		cAlgorithm.setBounds(149, 64, 207, 22);
		
		Button btnGoCal = new Button(grpChooseOptions, SWT.NONE);
		cOption.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				try{
				cAlgorithm.setItems(generateApproAlgorithms(cOption.getText()));
				}
				catch(Exception exc){
					MessageDialog.openError(shell, "Error", exc.getMessage());
				}
			}
		});
		btnGoCal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
				if(cAlgorithm.getText()==null||cAlgorithm.getText().equals("")||cOption.getText()==null||cOption.getText().equals("")){
					MessageDialog.openError(shell, "Alert", "You couldn't do calculations until select both option and algorithm!");
					return;
				}
				else	
				openResultPage(shell, cOption.getText(),cAlgorithm.getText());
				}
				catch(Exception exc){
					MessageDialog.openError(shell, "Error", "Error occured");
				}
			}
		});
		btnGoCal.setBounds(10, 103, 348, 28);
		btnGoCal.setText("Go For Calculation");
		generateStaticTable();

	}
	/**
	 * Create the result page in a new window
	 */
	protected  void openResultPage(final Shell shell, String optionSelected, String algoSelected) 
    {
        Shell resultPage = new Shell(shell, SWT.TITLE|SWT.SYSTEM_MODAL| SWT.CLOSE | SWT.MAX);
        createResultPageContent(resultPage, optionSelected, algoSelected);
        resultPage.layout();
        resultPage.open();
    }
	/**
	 * Create the content of result page
	 */
	protected void createResultPageContent(final Shell shell, String optionSelected, final String algoSelected) 
    {   shell.setSize(418, 418);
        shell.setLayout(new RowLayout(SWT.HORIZONTAL));
		Group grpSelectedOptionsAnd = new Group(shell, SWT.NONE);
		grpSelectedOptionsAnd.setBounds(3, 3, 415, 53);
		grpSelectedOptionsAnd.setText("Selected Options and Algorithms");
		
		Label lblOption = new Label(grpSelectedOptionsAnd, SWT.NONE);
		lblOption.setBounds(10, 10, 55, 21);
		lblOption.setText("Option:");
		
		Label optionVal = new Label(grpSelectedOptionsAnd, SWT.NONE);
		optionVal.setBounds(60, 10, 113, 21);
		optionVal.setText(optionSelected);
		Label lblAlgorithms = new Label(grpSelectedOptionsAnd, SWT.NONE);
		lblAlgorithms.setBounds(179, 10, 73, 21);
		lblAlgorithms.setText("Algorithms:");
		
		Label algoVal = new Label(grpSelectedOptionsAnd, SWT.NONE);
		algoVal.setBounds(247, 10, 159, 21);
		
		Group grpInputAttributes = new Group(shell, SWT.NONE);
		grpInputAttributes.setBounds(3, 59, 415, 170);
		grpInputAttributes.setText("Input Attributes");
		
		Label lblNewLabel = new Label(grpInputAttributes, SWT.NONE);
		lblNewLabel.setBounds(10, 13, 161, 22);
		lblNewLabel.setText("Current Stock Price:");
		
		txtStockPrice = new Text(grpInputAttributes, SWT.BORDER);
		txtStockPrice.setBounds(229, 10, 148, 22);
		
		Label lblStrikePrice = new Label(grpInputAttributes, SWT.NONE);
		lblStrikePrice.setText("Strike Price:");
		lblStrikePrice.setBounds(10, 41, 161, 22);
		
		txtStrikePrice = new Text(grpInputAttributes, SWT.BORDER);
		txtStrikePrice.setBounds(229, 41, 148, 22);
		
		Label lblStockVolitit = new Label(grpInputAttributes, SWT.NONE);
		lblStockVolitit.setText("Stock Volatility:");
		lblStockVolitit.setBounds(10, 70, 161, 22);
		
		txtVolatility = new Text(grpInputAttributes, SWT.BORDER);
		txtVolatility.setBounds(229, 69, 148, 22);
		
		Label lblRiskFreeInterest = new Label(grpInputAttributes, SWT.NONE);
		lblRiskFreeInterest.setText("Risk Free Interest:");
		lblRiskFreeInterest.setBounds(10, 98, 161, 22);
		
		txtRiskFreeInt = new Text(grpInputAttributes, SWT.BORDER);
		txtRiskFreeInt.setBounds(229, 97, 148, 22);
		
		Label txtYears = new Label(grpInputAttributes, SWT.NONE);
		txtYears.setText("The Terms of the Option in Years:");
		txtYears.setBounds(10, 126, 197, 22);
		
		txtYear = new Text(grpInputAttributes, SWT.BORDER);
		txtYear.setBounds(229, 126, 148, 22);
		
		Label label = new Label(grpInputAttributes, SWT.NONE);
		label.setBounds(383, 13, 18, 14);
		label.setText("$");
		
		Label label_1 = new Label(grpInputAttributes, SWT.NONE);
		label_1.setText("$");
		label_1.setBounds(383, 41, 18, 14);
		/**For generating the algorithm format***/
		generateInputContents(algoSelected,shell);
		Group grpResults = new Group(shell, SWT.NONE);
		grpResults.setLayoutData(new RowData(399, 79));
		grpResults.setText("Results:");
		algoVal.setText(algoSelected);
		optionVal.setText(optionSelected);
		final String[] optionAType=optionSelected.split(" ");
		final String type=algoSelected;
		Button btnResult = new Button(grpResults, SWT.NONE);
		btnResult.setBounds(10, 10, 160, 40);
		btnResult.setText("Get Price and Graph");
		
		final Label lblResultPrice = new Label(grpResults, SWT.NONE);
		lblResultPrice.setBounds(186, 30, 177, 14);
		
		Label lblPrice = new Label(grpResults, SWT.NONE);
		lblPrice.setBounds(222, 10, 113, 14);
		lblPrice.setText("Option Price:");
		btnResult.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String errorMessage="";
				try{
				errorMessage="Stock Price should be in double format!";
				double stockp=Double.parseDouble(txtStockPrice.getText());
				errorMessage="Strike Price should be in double format!";
				double strikep=Double.parseDouble(txtStrikePrice.getText());
				errorMessage="Volatility should be in double format!";
				double v=Double.parseDouble(txtVolatility.getText());
				errorMessage="Risk Free Rate should be in double format!";
				double r=Double.parseDouble(txtRiskFreeInt.getText());
				errorMessage="Terms of Year should be in double format!";
				double y=Double.parseDouble(txtYear.getText());
				ArrayList vars=new ArrayList();
				vars.add(stockp);
				vars.add(strikep);
				vars.add(r);
				vars.add(v);
				vars.add(y);
				if(optionAType[1].equals("Call"))
				vars.add(new Boolean(true));
				else
					vars.add(new Boolean(false));	
				vars.add(type);
				errorMessage="Additional Attribute Formatting Error!";
				generateVariables(algoSelected,vars);
				AlgorithmFactory factory=new AlgorithmFactory();
				Algorithm alg=factory.createAlgorithm(algoSelected, vars);
				double resultPrice=alg.doCalculation();
				DecimalFormat formatResult=new DecimalFormat("###.##");
				lblResultPrice.setText(formatResult.format(resultPrice));
				alg.doGraph();
				}
				catch(Exception exc){
					MessageDialog.openError(shell, "Error", errorMessage);
				}
				
			}
		});
		
		
		Canvas canvas = new Canvas(grpResults, SWT.NONE);
		canvas.setBounds(110, 30, 296, 124);
		
		Label lblOoadTeam = new Label(shell, SWT.NONE);
		lblOoadTeam.setBounds(92, 550, 174, 14);
		lblOoadTeam.setText("OOAD Team 4");

    }
	/**
	 * Based on the Option that user chose, generate suitable algorithms list dynamically
	 **/
	protected String[] generateApproAlgorithms(String choice){
		ArrayList<String> algorithms=new ArrayList<String>();
		OptionFactory factory=new OptionFactory();
		Option product=factory.createProducts(choice);
		if(product.getDoBinomialTree())
			algorithms.add("Binomial Tree");
		if(product.getDoBSFormular())
			algorithms.add("B-S Formula");
		if(product.getDoFiniteDifferences())
			algorithms.add("Numerical Integration");
		if(product.getDoSimulation())
			algorithms.add("Simulation");
		String[] result=new String[algorithms.size()];
		for(int i=0;i<result.length;i++){
			result[i]=algorithms.get(i);
		}
		return result;
	}
	/**
	 * Dynamically generate additional attribute content based on algorithms
	 **/
	protected void generateInputContents( String algo,final Shell shell){
			if(algo.equals("Binomial Tree")){
				binomialTreeAddtion(shell);
			}
			if(algo.equals("Numerical Integration")){
				numericalIntegrationAddtion(shell);
			}
			if(algo.equals("Simulation")){
				simulationAddtion(shell);
			}	
	}
	/**
	 * Dynamically Adding additional inputs based on algorithms
	 **/
	protected void generateVariables( String algo, ArrayList vars){
		if(algo.equals("Binomial Tree")){
			binomialTreeAddVar(vars);
		}
		if(algo.equals("Numerical Integration")){
			numericalIntegrationAddVar(vars);
		}
		if(algo.equals("Simulation")){
			simulationAddVar(vars);
		}	
	}
	/**
	 * For Binomial Tree 
	 **/
	protected void binomialTreeAddtion(final Shell shell){
		
		Group group = new Group(shell, SWT.NONE);
		group.setLayoutData(new RowData(398, SWT.DEFAULT));
		
		Label lblTimeIntervals = new Label(group, SWT.NONE);
		lblTimeIntervals.setText("Time Intervals:");
		lblTimeIntervals.setBounds(10, 14, 161, 22);
		
		spinTimeInterval = new Spinner(group, SWT.BORDER);
		spinTimeInterval.setMaximum(9999);
		spinTimeInterval.setBounds(232, 9, 98, 22);
		System.out.println("biganto print group");
	}
	protected void binomialTreeAddVar(ArrayList vars){
		vars.add(Integer.parseInt(spinTimeInterval.getText()));
	}
	/**
	 * For Numerical Integration 
	 **/
	protected void numericalIntegrationAddtion(final Shell shell){
		Group group = new Group(shell, SWT.NONE);
		group.setLayoutData(new RowData(398, 100));
		
		Label lblTimeIntervals = new Label(group, SWT.NONE);
		lblTimeIntervals.setText("Time Intervals:");
		lblTimeIntervals.setBounds(10, 14, 161, 22);
		
		spinTimeInterval = new Spinner(group, SWT.BORDER);
		spinTimeInterval.setMaximum(9999);
		spinTimeInterval.setBounds(232, 9, 98, 22);
		
		Label lblNumberOfTrials = new Label(group, SWT.NONE);
		lblNumberOfTrials.setText("Number of Price Intervals:");
		lblNumberOfTrials.setBounds(10, 42, 161, 22);
		
		priceIntervals = new Spinner(group, SWT.BORDER);
		priceIntervals.setMaximum(9999);
		priceIntervals.setBounds(232, 42, 98, 22);
		
		Label lblMaximumPrice = new Label(group, SWT.NONE);
		lblMaximumPrice.setText("Maximum Price:");
		lblMaximumPrice.setBounds(10, 70, 161, 22);
		
		maxPrice = new Text(group, SWT.BORDER);
		maxPrice.setBounds(232, 70, 148, 22);
		
		Label label_2 = new Label(group, SWT.NONE);
		label_2.setText("$");
		label_2.setBounds(386, 70, 18, 22);
		
		}
	protected void numericalIntegrationAddVar(ArrayList vars){
		vars.add(Integer.parseInt(spinTimeInterval.getText()));
		vars.add(Integer.parseInt(priceIntervals.getText()));
		vars.add(Double.parseDouble(maxPrice.getText()));
	}
	/**
	 * For Simulation 
	 **/
	protected void simulationAddtion(final Shell shell){
		Group group = new Group(shell, SWT.NONE);
		group.setLayoutData(new RowData(398, 80));
		
		Label lblTimeIntervals = new Label(group, SWT.NONE);
		lblTimeIntervals.setText("Time Intervals:");
		lblTimeIntervals.setBounds(10, 14, 161, 22);
		
		spinTimeInterval = new Spinner(group, SWT.BORDER);
		spinTimeInterval.setMaximum(9999);
		spinTimeInterval.setBounds(232, 9, 98, 22);
		
		Label lblNumberOfTrials = new Label(group, SWT.NONE);
		lblNumberOfTrials.setText("Number of Trials:");
		lblNumberOfTrials.setBounds(10, 42, 161, 22);
		
		spinTrials = new Spinner(group, SWT.BORDER);
		spinTrials.setMaximum(9999);
		spinTrials.setBounds(232, 42, 98, 22);
		
	}
	protected void simulationAddVar(ArrayList vars){	
		vars.add(Integer.parseInt(spinTrials.getText()));
		vars.add(Integer.parseInt(spinTimeInterval.getText()));;
	}
	/**
	 * For Static Table 
	 **/
	protected void generateStaticTable(){
		staticTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		staticTable.setBounds(10, 171, 398, 122);
		staticTable.setHeaderVisible(true);
		staticTable.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn_4 = new TableColumn(staticTable, SWT.NONE);
		tblclmnNewColumn_4.setWidth(67);
		
		TableColumn tblclmnNewColumn = new TableColumn(staticTable, SWT.NONE);
		tblclmnNewColumn.setWidth(76);
		tblclmnNewColumn.setText("B-S Formula");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(staticTable, SWT.NONE);
		tblclmnNewColumn_1.setWidth(83);
		tblclmnNewColumn_1.setText("Binomial Tree");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(staticTable, SWT.NONE);
		tblclmnNewColumn_2.setWidth(104);
		tblclmnNewColumn_2.setText("Num Integration");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(staticTable, SWT.NONE);
		tblclmnNewColumn_3.setWidth(75);
		tblclmnNewColumn_3.setText("Simulation");
		
		TableItem tableItem = new TableItem(staticTable, SWT.NONE);
		tableItem.setText(new String[] {"USA Call", "Yes", "Yes", "Yes", "Yes"});
		
		TableCursor tableCursor = new TableCursor(staticTable, SWT.NONE);
		
		TableItem tableItem_1 = new TableItem(staticTable, SWT.NONE);
		tableItem_1.setText(new String[] {"USA Put", "No", "Yes", "Yes", "Yes"});
		
		TableItem tableItem_2 = new TableItem(staticTable, SWT.NONE);
		tableItem_2.setText(new String[] {"Euro Call", "Yes", "Yes", "Yes", "Yes"});
		
		TableItem tableItem_3 = new TableItem(staticTable, SWT.NONE);
		tableItem_3.setText(new String[] {"Euro Put", "Yes", "Yes", "Yes", "Yes"});
		
		TableItem tableItem_4 = new TableItem(staticTable, SWT.NONE);
		tableItem_4.setText(new String[] {"Asian Call", "No", "No", "No", "Yes"});
		
		TableItem tableItem_5 = new TableItem(staticTable, SWT.NONE);
		tableItem_5.setText(new String[] {"Asian Put", "No", "No", "No", "Yes"});
	}
}

