import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
//import com.mysql.jdbc.Connection;
public class Contact extends JFrame {
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JPanel contentPane;
	private JTextField textPrenom;
	private JTextField textNom;
	private JTextField textTel;
	private JTable table;
	DefaultTableModel mod=new DefaultTableModel();
	private JTextField textField;
	private JTextField textRecherche;
	private JTextField textId;
	private void connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost/gestiondecontact","root","");
		}catch (Exception e) {
			
		}
	}
	public void listeContact() {
		mod.addColumn("id_contact");
		mod.addColumn("Prenom");
		mod.addColumn("Nom");
		mod.addColumn("Telephone");
		try {
			connect();
			String sql="select * from contact";
			pst=con.prepareStatement(sql);
			rs=pst.executeQuery();
			while(rs.next()) {
				mod.addRow(new Object [] {
						rs.getString("id_contact"),
						rs.getString("Prenom"),
						rs.getString("Nom"),
						rs.getString("Telephone")
				});
			}
			con.close();
		}catch(SQLException ex) {
			Logger.getLogger(Contact.class.getName()).log(Level.SEVERE, null, ex);
		}
						table.setModel(mod);
	}
	public void update() {
		   connect();
	          String sql = " Select*from contact";
	        try {
	             pst=con.prepareStatement(sql);
	             rs=pst.executeQuery();
	             table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		        
	}
	public void effacer() {
		textId.setText("");
		textPrenom.setText("");
		textNom.setText("");
		textTel.setText("");
	}
	//cette méthode permet de réinitialisé le tableau pour qu'il n'est pas de doublons
	public void refresh() {
		for(int i=mod.getRowCount();i>0;i--) {
			mod.removeRow(i-1);
		}
	}
	public void rechercheContact() {
		try {
			refresh();
			connect();
			String rec=textRecherche.getText().toUpperCase().replace(" ","");
			String requete=("select * contact where Nom like '"+rec+"%'");
			pst=con.prepareStatement(requete);
			rs=pst.executeQuery();
			while(rs.next()) {
				Object gd[]= {rs.getString("id_contact"),rs.getString("Prenom"),rs.getString("Nom"),rs.getString("Telephone")};
				mod.addRow(gd);
			}
			table.setModel(mod);
			
		}catch(Exception ex) {
			Logger.getLogger(Contact.class.getName()).log(Level.SEVERE,null,ex);
		}
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Contact frame = new Contact();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Contact() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 415);
		contentPane = new JPanel();
		contentPane.setForeground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setForeground(Color.BLACK);
		panel.setBounds(0, 0, 806, 389);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBorder(null);
		panel_1.setForeground(Color.WHITE);
		panel_1.setBounds(0, 0, 806, 47);
		panel.add(panel_1);
		
		JLabel gestionContact = new JLabel("GESTION DE CONTACT");
		gestionContact.setIcon(null);
		gestionContact.setForeground(Color.WHITE);
		gestionContact.setBackground(Color.GRAY);
		gestionContact.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel_1.add(gestionContact);
		
		JLabel labelPrenom = new JLabel("Prenom");
		labelPrenom.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelPrenom.setBounds(20, 143, 60, 13);
		panel.add(labelPrenom);
		
		textPrenom = new JTextField();
		textPrenom.setFont(new Font("Tahoma", Font.BOLD, 12));
		textPrenom.setColumns(10);
		textPrenom.setBounds(135, 140, 146, 19);
		panel.add(textPrenom);
		
		JLabel labelNom = new JLabel("Nom");
		labelNom.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelNom.setBounds(20, 192, 45, 13);
		panel.add(labelNom);
		
		textNom = new JTextField();
		textNom.setFont(new Font("Tahoma", Font.BOLD, 14));
		textNom.setColumns(10);
		textNom.setBounds(135, 188, 146, 19);
		panel.add(textNom);
		
		JLabel labelTel = new JLabel("Tel");
		labelTel.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelTel.setBounds(20, 236, 45, 13);
		panel.add(labelTel);
		textTel = new JTextField();
		textTel.setFont(new Font("Tahoma", Font.BOLD, 12));
		textTel.setColumns(10);
		textTel.setBounds(135, 233, 146, 19);
		panel.add(textTel);
		JButton buttonSave = new JButton("Ajouter");
		buttonSave.setIcon(new ImageIcon(Contact.class.getResource("/image/ajouter.PNG")));
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect();
					pst=con.prepareStatement("INSERT INTO `contact`(`Prenom`, `Nom`, `Telephone`) VALUES (?,?,?)");
					pst.setString(1,textPrenom.getText());
					pst.setString(2,textNom.getText());
					pst.setString(3,textTel.getText());
					pst.executeUpdate();
					//con.close();
					JOptionPane.showMessageDialog(null,textPrenom.getText()+ " Ajouter");
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				effacer();
				update();
			}	
		});
		buttonSave.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonSave.setBounds(39, 274, 113, 32);
		panel.add(buttonSave);
		
		JButton buttonUpdate = new JButton("Modifier");
		buttonUpdate.setIcon(new ImageIcon(Contact.class.getResource("/image/modifier.PNG")));
		buttonUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			            try {
			            	connect();
			            	int ligne=table.getSelectedColumn();
			            	String matri=table.getModel().getValueAt(ligne,0).toString();
			            	if(JOptionPane.showConfirmDialog(null, "voulez vous modifiez?","Modification",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
			                pst = con.prepareStatement("UPDATE contact SET Prenom='"+textPrenom.getText()+"', Nom='"+textNom.getText()+"',Telephone='"+textTel.getText()+"' WHERE id_contact='"+matri+"'");
			                pst.executeUpdate();
			                JOptionPane.showMessageDialog(null, "Vous avez bien modifié");
			                update();
			            }
			            
			        } catch (SQLException ex) {
			            JOptionPane.showMessageDialog(null, "erreur de modification");
			            Logger.getLogger(Contact.class.getName()).log(Level.SEVERE, null, ex);
			        }
			       ;
			       effacer();
			    }
			}
		);
		buttonUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonUpdate.setBounds(173, 274, 116, 32);
		panel.add(buttonUpdate);
		
		JButton buttonDelete = new JButton("Supprimer");
		buttonDelete.setIcon(new ImageIcon(Contact.class.getResource("/image/supp.png")));
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        try {
		            pst = con.prepareStatement("delete from contact where Nom = ?");
		            pst.setString(1, String.valueOf(textNom.getText()));
		            if(JOptionPane.showConfirmDialog(null, "voulez vous vraiment supprimer ?" , "Suppression", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION);

		        if (pst.executeUpdate() != 0) {
		        	JOptionPane.showMessageDialog(null, "Contact supprimé");
		        } else {
		            JOptionPane.showMessageDialog(null, "Veuillez entrer l'identifiant");
		        }
		        update();
		        effacer();
		        } catch (SQLException ex) {
		            Logger.getLogger(Contact.class.getName()).log(Level.SEVERE, null, ex);
		        } 
			}
		});
		buttonDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonDelete.setBounds(42, 330, 239, 32);
		panel.add(buttonDelete);
		
		JButton buttonSearch = new JButton("Recherche");
		buttonSearch.setIcon(new ImageIcon(Contact.class.getResource("/image/recherche.PNG")));
		buttonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rechercheContact();
				
			}
		});
		buttonSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonSearch.setBounds(408, 101, 135, 32);
		panel.add(buttonSearch);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 143, 409, 219);
		panel.add(scrollPane);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel mod=(DefaultTableModel)table.getModel();
				int index=table.getSelectedRow();
				textId.setText(mod.getValueAt(index, 0).toString());
				textPrenom.setText(mod.getValueAt(index, 1).toString());
				textNom.setText(mod.getValueAt(index, 2).toString());
				textTel.setText(mod.getValueAt(index, 3).toString());	
				
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		
		textField = new JTextField();
		textField.setBounds(668, 125, -3, 19);
		panel.add(textField);
		textField.setColumns(10);
		
		textRecherche = new JTextField();
		textRecherche.setFont(new Font("Tahoma", Font.BOLD, 12));
		textRecherche.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//rechercheContact();
			}
		});
		textRecherche.setBounds(553, 101, 211, 32);
		panel.add(textRecherche);
		textRecherche.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Liste des contacts");
		lblNewLabel.setForeground(SystemColor.text);
		lblNewLabel.setBackground(SystemColor.inactiveCaptionBorder);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(464, 57, 188, 28);
		panel.add(lblNewLabel);
		
		textId = new JTextField();
		textId.setFont(new Font("Tahoma", Font.BOLD, 12));
		textId.setColumns(10);
		textId.setBounds(135, 101, 146, 19);
		panel.add(textId);
		JLabel labelId = new JLabel("NumContact");
		labelId.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelId.setBounds(20, 101, 82, 13);
		panel.add(labelId);
		listeContact();
		update();
	}
}
