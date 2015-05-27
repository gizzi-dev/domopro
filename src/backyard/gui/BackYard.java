/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backyard.gui;

import model.backyard.tipoUtente;
import model.backyard.ScenarioSimulazione;
import model.backyard.UserInfo;
import model.backyard.InfoScenario;
import model.backyard.BackYardApplicationController;
import java.awt.CardLayout;
import java.awt.Container;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 * Questa classe fa da GUI Controller per le operazioni di Gestire Scenari di
 * Simulazione. Il tag @OPERATION indica dove bisogna inserire le invocazioni
 * alle operazioni di sistema.
 *
 * @author picardi
 */
public class BackYard extends javax.swing.JFrame {

	private static Container mainPane;

	private DefaultListModel<InfoScenario> scenariListModel;
	private ScenarioPanel scenarioPanel;
	private UserInfo uInfo;        

	/**
	 * Creates new form BackYardMainWindow
	 */
	public BackYard() {
                super("BackYard");
		initComponents();
		// Inizializzazione elementi di interfaccia
		scenariListModel = new DefaultListModel<>();
		this.scenariList.setModel(scenariListModel);
		apriButton.setEnabled(false);
		duplicaButton.setEnabled(false);
		eliminaButton.setEnabled(false);
		scenarioPanel = new ScenarioPanel(this);
		getContentPane().add(scenarioPanel, "DettagliScenario");
		mainPane = getContentPane();                
	}

	public static Container getMainPane() {
		return mainPane;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 * @return 
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userPanel = new javax.swing.JPanel();
        javax.swing.JPanel userInnerP = new javax.swing.JPanel();
        javax.swing.JLabel usernameLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        usernameInnerSP = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        elencoScenariPanel = new javax.swing.JPanel();
        javax.swing.JPanel elencoScenariCPan = new javax.swing.JPanel();
        javax.swing.JScrollPane scenariListScroll = new javax.swing.JScrollPane();
        scenariList = new javax.swing.JList();
        javax.swing.JLabel elencoScenariLabel = new javax.swing.JLabel();
        javax.swing.JPanel elencoScenariSP = new javax.swing.JPanel();
        nuovoButton = new javax.swing.JButton();
        apriButton = new javax.swing.JButton();
        eliminaButton = new javax.swing.JButton();
        duplicaButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.CardLayout());

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout();
        flowLayout1.setAlignOnBaseline(true);
        userPanel.setLayout(flowLayout1);

        userInnerP.setLayout(new java.awt.BorderLayout());

        usernameLabel.setText("Inserisci il tuo identificativo e e premi Ok per iniziare:");
        userInnerP.add(usernameLabel, java.awt.BorderLayout.NORTH);
        userInnerP.add(usernameField, java.awt.BorderLayout.CENTER);

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        usernameInnerSP.add(okButton);

        userInnerP.add(usernameInnerSP, java.awt.BorderLayout.SOUTH);

        userPanel.add(userInnerP);

        getContentPane().add(userPanel, "User");

        elencoScenariPanel.setLayout(new java.awt.BorderLayout());

        elencoScenariCPan.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4))));
        elencoScenariCPan.setLayout(new java.awt.BorderLayout());

        scenariList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scenariList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                scenariListValueChanged(evt);
            }
        });
        scenariListScroll.setViewportView(scenariList);

        elencoScenariCPan.add(scenariListScroll, java.awt.BorderLayout.CENTER);

        elencoScenariLabel.setText("Scenari di simulazione disponibili:");
        elencoScenariCPan.add(elencoScenariLabel, java.awt.BorderLayout.NORTH);

        elencoScenariPanel.add(elencoScenariCPan, java.awt.BorderLayout.CENTER);

        nuovoButton.setText("Nuovo...");
        nuovoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuovoButtonActionPerformed(evt);
            }
        });
        elencoScenariSP.add(nuovoButton);

        apriButton.setText("Apri");
        apriButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apriButtonActionPerformed(evt);
            }
        });
        elencoScenariSP.add(apriButton);

        eliminaButton.setText("Elimina");
        eliminaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminaButtonActionPerformed(evt);
            }
        });
        elencoScenariSP.add(eliminaButton);

        duplicaButton.setText("Duplica...");
        duplicaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                duplicaButtonActionPerformed(evt);
            }
        });
        elencoScenariSP.add(duplicaButton);

        elencoScenariPanel.add(elencoScenariSP, java.awt.BorderLayout.SOUTH);

        getContentPane().add(elencoScenariPanel, "ElencoScenari");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
		// Ottiene il testo inserito dall'utente
		String nomeUtente = this.usernameField.getText().trim();
		if (nomeUtente.length() > 0) { // Il nome utente e' non vuoto
			/* @MODELINTERACTION crea un oggetto UserInfo con il nome specificato e tipo=TECNICO
			 * variabili id e password al momento non usate (non c'e' un vero login ne' un
			 * DB degli utenti - se ci fosse qui dovremmo collegarci e reperire le informazioni
			 * (id e tipo) a partire da username e password. 
			 * E' dunque atteso in UserInfo un costruttore:
			 * UserInfo(String, UserInfo.TipoUtente)
			 * (modificare in caso si sia usato un modo diverso x rappresentare il tipo)
			 */
			// @EXAMPLES T2&T3
			uInfo = new UserInfo(nomeUtente, tipoUtente.TECNICO);
                        BackYardApplicationController app = new BackYardApplicationController(uInfo);

			/* @OPERATIONottieniScenariDiSimulazione
			 */
			// @EXAMPLES T2/T3:
			//ArrayList<InfoScenario> scenari = BackYardCtrl.ottieniElencoScenari(uInfo);
			ArrayList<InfoScenario> scenari = new ArrayList<>();
                        try {
                            scenari = BackYardApplicationController.getAppController().OttieniScenariDiSimulazione(uInfo);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "Errore nella connessione col DB", "Errore", JOptionPane.ERROR_MESSAGE);
                        }

			/* Inizializzazione della lista di scenari
			 * @MODELINTERACTION la visualizzazione degli oggetti nell'elenco è basata sul metodo
			 * toString degli oggetti stessi. Implementarlo appropriatamente
			 */
			this.aggiornaElencoScenari(scenari);

			// Mostra il pannello con l'elenco degli scenari
			((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "ElencoScenari");

		} else { // L'utente non ha inserito alcun testo
			JOptionPane.showMessageDialog(this, "Nome utente non valido", "Errore", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_okButtonActionPerformed

    private void scenariListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_scenariListValueChanged
		if (!evt.getValueIsAdjusting()) {
			int sel = scenariList.getSelectedIndex();
			this.apriButton.setEnabled(sel >= 0);
			this.eliminaButton.setEnabled(sel >= 0);
			this.duplicaButton.setEnabled(sel >= 0);
		}
    }//GEN-LAST:event_scenariListValueChanged

    private void eliminaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminaButtonActionPerformed
		// Ottiene l'oggetto InfoScenario selezionato nell'elenco		
		InfoScenario scen = (InfoScenario) scenariList.getSelectedValue();
		
		/* @OPERATIONeliminaScenario
		 */

		// @EXAMPLES T2/T3:
		// ArrayList<InfoScenario> scenari = BackYardCtrl.eliminaScenario(uInfo);
		ArrayList<InfoScenario> scenari = BackYardApplicationController.getAppController().eliminaScenario(scen);

		// Aggiornamento view
		this.aggiornaElencoScenari(scenari);

    }//GEN-LAST:event_eliminaButtonActionPerformed

    private void duplicaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_duplicaButtonActionPerformed
		// Ottiene l'oggetto InfoScenario selezionato nell'elenco
		InfoScenario scInfo = (InfoScenario) scenariList.getSelectedValue();

		// Chiede il nuovo nome (e' possibile che l'utente prema cancel rinunciando all'operazione)
		NomeScenarioDialog dia = new NomeScenarioDialog(this);
		dia.setVisible(true);

		if (dia.getValue() == JOptionPane.OK_OPTION) { // L'utente ha premuto Ok

			// Determina il nome inserito
			String nuovoNome = dia.getNome();

			if (nuovoNome.length() > 0) { // Il nome inserito e' non vuoto
				/* @OPERATIONduplicaScenario
				 * in base a come si è gestito il caso di nome non valido, bisogna o catturare
				 * l'eccezione o verificare il valore restituito dall'operazione. In ogni
				 * caso si può mostrare la stessa message dialog che qui viene visualizzata quando l'utente
				 * non inserisce NESSUN NOME.
				 */

				// @EXAMPLES T2/T3:
				// ArrayList<InfoScenario> scenari = BackYardCtrl.duplicaScenario(uInfo);
				ArrayList<InfoScenario> scenari= null;
                                    try {
                                        scenari = BackYardApplicationController.getAppController().duplicaScenario(scInfo, nuovoNome);
                                    } catch (Exception ex) {
                                        Logger.getLogger(BackYard.class.getName()).log(Level.SEVERE, null, ex);
                                    }

				// Aggiornamento view
				this.aggiornaElencoScenari(scenari);
			} else { // Il nome inserito e' vuoto
				JOptionPane.showMessageDialog(this, "Nome scenario non valido", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
    }//GEN-LAST:event_duplicaButtonActionPerformed

    private void apriButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apriButtonActionPerformed
		// Ottiene l'oggetto InfoScenario selezionato nell'elenco
		InfoScenario scInfo = (InfoScenario) scenariList.getSelectedValue();
		/* @OPERATIONapriScenario
		 */

		// @EXAMPLES T2/T3:
		// ScenarioSimulazione scenario = BackYardCtrl.apriScenario(scInfo);
		ScenarioSimulazione scenario= null;
                try {
                    scenario = BackYardApplicationController.getAppController().apriScenario(scInfo);
                } catch (Exception ex) {
                    Logger.getLogger(BackYard.class.getName()).log(Level.SEVERE, null, ex);
                }

		// inizializza la vista dello scenario
		scenarioPanel.setup(scenario);
		// mostra il pannello dello scenario
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "DettagliScenario");
    }//GEN-LAST:event_apriButtonActionPerformed

    private void nuovoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuovoButtonActionPerformed
		// Chiede il  nome (e' possibile che l'utente prema cancel rinunciando all'operazione)
		NomeScenarioDialog dia = new NomeScenarioDialog(this);
		dia.setVisible(true);

		if (dia.getValue() == JOptionPane.OK_OPTION) { // L'utente ha premuto Ok

			// Ottiene il nome inserito
			String nuovoNome = dia.getNome();
                        
			if (nuovoNome.length() > 0) { // Il nome inserito e' non vuoto
				/* @OPERATIONcreaNuovoScenario
				 * in base a come si è gestito il caso di nome non valido, bisogna o catturare
				 * l'eccezione o verificare il valore restituito dall'operazione. In ogni
				 * caso si può mostrare la stessa message dialog che qui viene visualizzata quando l'utente
				 * non inserisce NESSUN NOME.
				 */

				// @EXAMPLES T2/T3:
				// ScenarioSimulazione scenario = BackYardCtrl.creaNuovoScenario(uInfo, nuovoNome);
				ScenarioSimulazione nuovo= null;
                                try {
                                    nuovo = BackYardApplicationController.getAppController().creaNuovoScenario(uInfo, nuovoNome);
                                } catch (Exception ex) {
                                    Logger.getLogger(BackYard.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
				// Inizializzazione vista
				if(nuovo!= null){                                    
                                    scenarioPanel.setup(nuovo);
                                    // Visualizza il pannello con le info dello scenario				
                                    ((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "DettagliScenario");
                                }
                                else { // Il nome inserito e' gia' presente
                                    JOptionPane.showMessageDialog(this, "Nome scenario esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                                }
			} else { // Il nome inserito e' vuoto
				JOptionPane.showMessageDialog(this, "Nome scenario non valido", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
    }//GEN-LAST:event_nuovoButtonActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(BackYard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(BackYard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(BackYard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(BackYard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>
		//</editor-fold>
		//</editor-fold>
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				/* @MODELINTERACTION qui va inizializzato il GRASP controller principale, su cui
				 * invocare le operazioni di "Gestire Scenari". 
				 * E' atteso in BackYardApplicationController un metodo:
				 * - initApplication(): void
				 */
				BackYardApplicationController.initApplication();

				// crea la vista principale e la mostra
				BackYard mainView = new BackYard();
				mainView.setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton apriButton;
    private javax.swing.JButton duplicaButton;
    private javax.swing.JPanel elencoScenariPanel;
    private javax.swing.JButton eliminaButton;
    private javax.swing.JButton nuovoButton;
    private javax.swing.JButton okButton;
    private javax.swing.JList scenariList;
    private javax.swing.JPanel userPanel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JPanel usernameInnerSP;
    // End of variables declaration//GEN-END:variables

	private void aggiornaElencoScenari(ArrayList<InfoScenario> scenari) {
		this.scenariListModel.removeAllElements();
		for (InfoScenario sc : scenari) {
			this.scenariListModel.addElement(sc);
		}

		this.scenariList.clearSelection();
	}

	void chiudiScenario(java.awt.event.ActionEvent evt) {
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "ElencoScenari");
		
		this.okButtonActionPerformed(evt);
	}
}
