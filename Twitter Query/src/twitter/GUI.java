package twitter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager.LookAndFeelInfo;

import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private JPanel contentPane;
	private JPanel north;
	private JPanel northWest;
	private JPanel northEast;
	private JPanel northCenter;
	private JPanel northSouth;
	private JPanel center;
	private JPanel south;
	private JPanel versionTracking;
	private JTable table;
	private Object columns[];
	private Object[][] tableContents;
	private TweetController tc = new TweetController();
	private JComboBox<String> trendLocations;
	private JComboBox<String> numTweets;

	// launch the application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// look for the Nimbus look and feel and
					// apply it to the UI if found
					for (LookAndFeelInfo info : UIManager
							.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}

					GUI frame = new GUI();

					// get the screen size
					Dimension screenSize = Toolkit.getDefaultToolkit()
							.getScreenSize();

					// maximize the application
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

					// make the frame the size of the screen
					frame.setSize(screenSize);

					// make the frame visible
					frame.setVisible(true);

					// disable resizing of the application
					frame.setResizable(false);

					// close the application when the red x is clicked
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// create the frame
	public GUI() {
		setTitle("BlueTread Twitter Query");
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		north = new JPanel(new BorderLayout(0, 0));
		northWest = new JPanel();
		northEast = new JPanel();
		northCenter = new JPanel();
		northSouth = new JPanel();
		center = new JPanel();
		versionTracking = new JPanel();
		south = new JPanel(new BorderLayout(0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// swing components
		JLabel logo = new JLabel();
		JLabel fbLogo = new JLabel();
		JLabel tLogo = new JLabel();
		JLabel title = new JLabel("BlueTread Twitter Query");
		numTweets = new JComboBox<String>();
		JTextField queryBox = new JTextField("Enter a query...");
		JButton btnSearch = new JButton("Search");
		JButton btnClear = new JButton("Clear");
		trendLocations = new JComboBox<String>();
		JButton findTrends = new JButton("View Trends");
		JTextField statusBox = new JTextField("Enter a tweet...");
		JButton btnStatus = new JButton("Tweet");
		JLabel spacerLbl = new JLabel("   |   ");
		JLabel spacerLbl2 = new JLabel("   |   ");
		JLabel version = new JLabel("Version (1.0.0)");
		JButton retweet = new JButton("Retweet");
		JCheckBox toggleRTs = new JCheckBox("Filter Retweets");
		
		// set tooltips for swing components
		numTweets.setToolTipText("Number of latest tweets to display in the table");
		queryBox.setToolTipText("Any hashtag or keyword you want to query");
		btnSearch.setToolTipText("Click to apply query");
		btnClear.setToolTipText("Clear current query settings and table results");
		trendLocations.setToolTipText("Any city or country with trends");
		findTrends.setToolTipText("Search the selected location for trends");
		statusBox.setToolTipText("Enter a tweet to post to your twitter");
		btnStatus.setToolTipText("Send your valid tweet");
		retweet.setToolTipText("Retweet the selected users tweet or their retweet");
		toggleRTs.setToolTipText("Filter out retweets");
		
		// add background color to panels
		north.setBackground(Color.decode("#024E82"));
		northCenter.setBackground(Color.decode("#024E82"));
		northWest.setBackground(Color.decode("#024E82"));
		northEast.setBackground(Color.decode("#024E82"));
		northSouth.setBackground(Color.decode("#024E82"));
		center.setBackground(Color.decode("#024E82"));
		south.setBackground(Color.decode("#024E82"));
		versionTracking.setBackground(Color.decode("#024E82"));
		contentPane.setBackground(Color.decode("#024E82"));

		// set fonts for labels and checkbox
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Pristina", Font.PLAIN, 48));
		spacerLbl.setForeground(Color.WHITE);
		spacerLbl.setFont(new Font("Arial", Font.PLAIN, 24));
		spacerLbl2.setForeground(Color.WHITE);
		spacerLbl2.setFont(new Font("Arial", Font.PLAIN, 24));
		version.setForeground(Color.WHITE);
		version.setFont(new Font("proxima-nova", Font.PLAIN, 14));
		toggleRTs.setForeground(Color.WHITE);
		toggleRTs.setFont(new Font("proxima-nova", Font.PLAIN, 14));

		// add the logo to a label
		URL urlBT = GUI.class.getResource("/resources/BT Logo2.jpg");
		URL urlFB = GUI.class.getResource("/resources/facebookIcon.png");
		URL urlTW = GUI.class.getResource("/resources/twitterIcon.png");
		ImageIcon image = new ImageIcon(urlBT);
		logo.setIcon(image);
		ImageIcon image2 = new ImageIcon(urlFB);
		fbLogo.setIcon(image2);
		ImageIcon image3 = new ImageIcon(urlTW);
		tLogo.setIcon(image3);
		
		// define text field properties
		queryBox.setBounds(40, 23, 86, 20);
		queryBox.setColumns(10);
		statusBox.setBounds(40, 23, 86, 20);
		statusBox.setColumns(10);

		// load all GUI components
		loadGui();

		// action listeners for buttons
		AbstractAction actionSearch = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String tweetsToDisplay = String.valueOf(numTweets
							.getSelectedItem());

					if (queryBox.getText().equals("Enter a query...")) {
						errorMessage("Please enter a valid query.", "Oops!");
					} else if (!queryBox.getText().equals("") && toggleRTs.isSelected()) {
						createTable("searching", queryBox.getText(),
								tweetsToDisplay, "filter");
						center.revalidate();
						center.repaint();
					} else if (!queryBox.getText().equals("") && !toggleRTs.isSelected()) {
						createTable("searching", queryBox.getText(),
								tweetsToDisplay, "no filter");
						center.revalidate();
						center.repaint();
					} else {
						errorMessage("Please enter a valid query.", "Oops!");
					}
				} catch (NumberFormatException e) {
					errorMessage("Please select a number of tweets.", "Oops!");
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		};
		btnSearch.addActionListener(actionSearch);
		queryBox.addActionListener(actionSearch);
		toggleRTs.addActionListener(actionSearch);

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to clear this data?\n",
							"Clear Data?", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (dialogResult == JOptionPane.YES_OPTION) {
						createTable("clearing", "", "","");
						queryBox.setText("Enter a query...");
						
						if(toggleRTs.isSelected()){
							toggleRTs.setSelected(false);
						}
						
						numTweets.setSelectedIndex(0);
						center.revalidate();
						center.repaint();
					}
				} catch (TwitterException e) {
					e.printStackTrace();
				}

			}
		});

		findTrends.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String locationIndex = String.valueOf(trendLocations
							.getSelectedIndex() - 1);
					String location = String.valueOf(trendLocations
							.getSelectedItem());
					createTable("trending", locationIndex, location,"");
					center.revalidate();
					center.repaint();
				} catch (ArrayIndexOutOfBoundsException e2) {
					errorMessage("Please select a location.", "Oops!");
				} catch (TwitterException e1) {
					errorMessage("Rate limit exceeded", "Slow down!");
				}
			}
		});

		btnStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String latestTweet = statusBox.getText();

					if (!latestTweet.equals("Enter a tweet...")) {
						tc.sendTweet(latestTweet);

						JOptionPane.showMessageDialog(null, "Tweet sent!",
								"Success", JOptionPane.INFORMATION_MESSAGE);

						statusBox.setText("Enter a tweet...");
					} 
					// default text has not been removed
					else {
						errorMessage("Please enter a valid tweet.", "Oops!");
					}
				} catch (TwitterException e1) {
					errorMessage("Please enter a valid tweet.", "Oops!");
				}
			}
		});

		retweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int selection = table.getSelectedRow();

					long tweetID = tc.getStatusID().get(selection);

					tc.sendReTweet(tweetID);

					JOptionPane.showMessageDialog(null, "Retweet sent!",
							"Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (NullPointerException e3) {
					errorMessage("Please select a tweet.", "Oops!");
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
		});

		queryBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				queryBox.setText("");
			}
		});

		statusBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				statusBox.setText("");
			}
		});

		logo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openWebpage("http://www.bluetread.com/#development-solutions");
			}
		});
		
		fbLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openWebpage("https://www.facebook.com/bluetread");
			}
		});
		
		tLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openWebpage("https://twitter.com/bluetreadhq");
			}
		});
		
		// add logo to the northWest panel
		northWest.add(logo);
		
		// add logos to the northEast panel
		northEast.add(fbLogo);
		northEast.add(tLogo);

		// add components to the northCenter panel
		northCenter.add(title);

		// add components to the northSouth panel
		northSouth.add(trendLocations);
		northSouth.add(findTrends);
		northSouth.add(spacerLbl);
		northSouth.add(toggleRTs);
		northSouth.add(numTweets);
		northSouth.add(queryBox);
		northSouth.add(btnSearch);
		northSouth.add(btnClear);
		northSouth.add(spacerLbl2);
		northSouth.add(statusBox);
		northSouth.add(btnStatus);
		northSouth.add(retweet);

		// add components to the north panel
		north.add(northWest, BorderLayout.WEST);
		north.add(northEast, BorderLayout.EAST);
		north.add(northCenter, BorderLayout.CENTER);
		north.add(northSouth, BorderLayout.SOUTH);

		// add components to the trends panel
		versionTracking.add(version);

		// add the trends panel to the south panel
		south.add(versionTracking, BorderLayout.EAST);

		// add the north and south panels to the main panel
		contentPane.add(north, BorderLayout.NORTH);
		contentPane.add(south, BorderLayout.SOUTH);
	}

	// Creates the table for viewing twitter queries and trends
	public void createTable(String status, String query, String tweetsToDisplay, String filterStatus)
			throws TwitterException {
		center.removeAll();

		// displaying search results to table
		if (status.equals("searching")) {
			columns = new Object[] { "Handle", "Tweet", "Time Posted" };
			try {
				ArrayList<Tweet> results = tc.searchTweets(query,
						tweetsToDisplay, filterStatus);

				int rows = results.size();
				int cols = columns.length;

				tableContents = extendTable(rows, cols);

				for (int i = 0; i < rows; i++) {
					if (results.get(0).getUsername().equals("EMPTY!!!")) {
						errorMessage("No results found...",
								"Huh, check that out!");
						break;
					}

					tableContents[i][0] = results.get(i).getUsername();
					tableContents[i][1] = results.get(i).getTweet();
					tableContents[i][2] = results.get(i).getDob();
				}
			} catch (TwitterException e) {
				errorMessage("Please enter a valid query.", "Oops!");
			}

			setupTable();
		}
		// displaying trends to table
		else if (status.equals("trending")) {
			Object[] locales = tc.getTrendLocations("");

			// labels the column with the selected location
			columns = new Object[] { tweetsToDisplay };

			Object selectedLocation = locales[Integer.parseInt(query)];
			String woeIdString = selectedLocation.toString();
			int woeId = Integer.parseInt(woeIdString);

			Object[] trendValues = tc.getTrends(woeId);

			int rows = trendValues.length;
			int cols = columns.length;

			tableContents = extendTable(rows, cols);

			for (int i = 0; i < rows; i++) {
				tableContents[i][0] = trendValues[i].toString();
			}

			setupTable();
		}
		// initial load of table
		else {
			tableContents = new Object[36][3];
			columns = new Object[] { "Handle", "Tweet", "Time Posted" };

			setupTable();
		}

	}

	public void loadGui() {
		// load empty table on boot
		try {
			createTable("booting", "", "","");
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}

		// populate trend locations combo box
		loadTrendLocations();

		// populate tweet amounts combo box
		loadTweetAmounts();
	}

	// fill trend locations drop down menu
	public void loadTrendLocations() {
		try {
			Object[] locationNames = tc.getTrendLocations("locations");

			for (int b = -1; b < locationNames.length; b++) {
				if (b == -1) {
					trendLocations.addItem("Select Location");
				} else {
					trendLocations.addItem(locationNames[b].toString());
				}
			}
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
	}

	// fill tweet drop down menu
	public void loadTweetAmounts() {
		for (int a = 0; a < 11; a++) {
			if (a == 0) {
				numTweets.addItem("Number of Tweets");
			} else {
				numTweets.addItem(String.valueOf(a * 10));
			}
		}
	}

	// build and fill table
	public void setupTable() {
		DefaultTableModel model = new DefaultTableModel(tableContents, columns);
		center.setLayout(new BorderLayout(0, 0));
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);

		TableColumn column = null;
		for (int k = 0; k < columns.length; k++) {
			column = table.getColumnModel().getColumn(k);
			if (k == 0) {
				column.setPreferredWidth(20);
			} else if (k == 1) {
				column.setPreferredWidth(900);
			} else if (k == 2) {
				column.setPreferredWidth(60);
			}
		}

		center.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(center, BorderLayout.CENTER);
	}

	// ensures the table always has enough rows to fill the screen
	public Object[][] extendTable(int rows, int cols) {
		if (rows < 36) {
			int minRows = 36;
			int emptyRows = Math.abs(minRows - rows);
			return tableContents = new Object[rows + emptyRows][cols];
		}
		return tableContents = new Object[rows][cols];
	}

	// reusable error message
	public void errorMessage(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.WARNING_MESSAGE);
	}

	// launch the browse and go to the url passed in
	public static void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}