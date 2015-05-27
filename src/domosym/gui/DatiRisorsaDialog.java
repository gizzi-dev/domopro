/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domosym.gui;

import backyard.gui.*;
import javax.swing.JOptionPane;

/**
 *
 * @author picardi
 */
public class DatiRisorsaDialog extends javax.swing.JDialog {

	private int value;
	/**
	 * Creates new form NomeScenarioDialog
	 */
	public DatiRisorsaDialog(java.awt.Frame parent) {
		super(parent, true);
		initComponents();
		value = JOptionPane.CANCEL_OPTION;
	}

	public String getNome()
	{
		return this.nomeRisorsaField.getText().trim();
	}
	
	public void setNome(String nome)
	{
		this.nomeRisorsaField.setText(nome);
	}
	
	public boolean getAConsumo()
	{
		return this.aConsumoCheck.isSelected();
	}
	
	public void setAConsumo(boolean acon)
	{
		this.aConsumoCheck.setSelected(acon);
		this.limiteTotField.setEnabled(acon);
		this.limiteTotLabel.setEnabled(acon);
		this.rinnovoField.setEnabled(acon);
		this.rinnovoLabel.setEnabled(acon);		
	}
	
	public void setLimite(int lim)
	{
		this.limiteField.setText(""+lim);
	}
	
	public void setLimiteTot(int limtot)
	{
		this.limiteTotField.setText(""+limtot);
	}
	
	public void setRinnovo(int rin)
	{
		this.rinnovoField.setText(""+rin);
	}
	
	public int getLimite()
	{
		return Integer.parseInt(this.limiteField.getText());
	}
	
	public int getLimiteTot()
	{
		return Integer.parseInt(this.limiteTotField.getText());
	}
	
	public int getRinnovo()
	{
		return Integer.parseInt(this.rinnovoField.getText());
	}
	
	public int getValue()
	{
		return this.value;
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

        jPanel5 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        nomeRisorsaField = new javax.swing.JTextField();
        aConsumoCheck = new javax.swing.JCheckBox();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        limiteField = new javax.swing.JTextField();
        limiteTotLabel = new javax.swing.JLabel();
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        limiteTotField = new javax.swing.JTextField();
        rinnovoLabel = new javax.swing.JLabel();
        javax.swing.JPanel jPanel4 = new javax.swing.JPanel();
        rinnovoField = new javax.swing.JTextField();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), null, new java.awt.Dimension(0, 32767));
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5))));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.Y_AXIS));

        jLabel1.setText("Inserisci il nome della risorsa");
        jLabel1.setAlignmentY(0.0F);
        jPanel5.add(jLabel1);

        nomeRisorsaField.setAlignmentX(0.0F);
        jPanel5.add(nomeRisorsaField);

        aConsumoCheck.setText("Risorsa a consumo?");
        aConsumoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aConsumoCheckActionPerformed(evt);
            }
        });
        jPanel5.add(aConsumoCheck);

        jLabel2.setText("Inserisci il limite di utilizzo per unità di tempo:");
        jPanel5.add(jLabel2);

        jPanel1.setAlignmentX(0.0F);
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        limiteField.setColumns(10);
        limiteField.setAlignmentX(0.0F);
        jPanel1.add(limiteField);

        jPanel5.add(jPanel1);

        limiteTotLabel.setText("Inserisci il limite di consumo totale:");
        limiteTotLabel.setEnabled(false);
        jPanel5.add(limiteTotLabel);

        jPanel3.setAlignmentX(0.0F);
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        limiteTotField.setColumns(10);
        limiteTotField.setAlignmentX(0.0F);
        limiteTotField.setEnabled(false);
        jPanel3.add(limiteTotField);

        jPanel5.add(jPanel3);

        rinnovoLabel.setText("Inserisci il numero di giorni dopo cui si rinnova:");
        rinnovoLabel.setEnabled(false);
        jPanel5.add(rinnovoLabel);

        jPanel4.setAlignmentX(0.0F);
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        rinnovoField.setColumns(10);
        rinnovoField.setAlignmentX(0.0F);
        rinnovoField.setEnabled(false);
        jPanel4.add(rinnovoField);

        jPanel5.add(jPanel4);

        filler2.setAlignmentX(0.0F);
        jPanel5.add(filler2);

        getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel2.setAlignmentX(0.0F);

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        jPanel2.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel2.add(cancelButton);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        try
		{
			Integer.parseInt(this.limiteField.getText());
			if (this.aConsumoCheck.isSelected())
			{
				Integer.parseInt(this.limiteTotField.getText());
				Integer.parseInt(this.rinnovoField.getText());
			}
		}
		catch(NumberFormatException exc)
		{
			JOptionPane.showMessageDialog(this, "I limiti e l'eventuale rinnovi devono essere numeri interi", 
				"Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.value = JOptionPane.OK_OPTION;
		this.setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
		this.value = JOptionPane.CANCEL_OPTION;
		this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void aConsumoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aConsumoCheckActionPerformed
        boolean aconsumo = this.aConsumoCheck.isSelected();
		this.limiteTotField.setEnabled(aconsumo);
		this.limiteTotLabel.setEnabled(aconsumo);
		this.rinnovoField.setEnabled(aconsumo);
		this.rinnovoLabel.setEnabled(aconsumo);
    }//GEN-LAST:event_aConsumoCheckActionPerformed

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
			java.util.logging.Logger.getLogger(DatiRisorsaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(DatiRisorsaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(DatiRisorsaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(DatiRisorsaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				DatiRisorsaDialog dialog = new DatiRisorsaDialog(new javax.swing.JFrame());
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox aConsumoCheck;
    private javax.swing.JButton cancelButton;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField limiteField;
    private javax.swing.JTextField limiteTotField;
    private javax.swing.JLabel limiteTotLabel;
    private javax.swing.JTextField nomeRisorsaField;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField rinnovoField;
    private javax.swing.JLabel rinnovoLabel;
    // End of variables declaration//GEN-END:variables
}
