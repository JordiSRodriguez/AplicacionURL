package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

/**
 * Clase que contiene la interfaz gráfica de la aplicación.
 */
public class AplicacionURL extends JFrame implements ActionListener {

    private JTextField urlField;
    private JTextArea outputArea;
    private JButton limpiarButton;
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JButton ayudaButton;
    private JLabel autorLabel;
    private JPanel panelPing;
    private JTextArea pingArea;
    private JPanel panelTracert;
    private JTextArea tracertArea;
    private JPanel panelNslookup;
    private JTextArea nslookupArea;
    private JPanel panelCurl;
    private JTextArea curlArea;
    private JPanel panelTelnet;
    private JTextArea telnetArea;
    private JTabbedPane tabbedPane;
    private static final String AUTOR = "Jordi Sumba";

    private Process process;

    /**
     * Método principal de la aplicación.
     * @throws IOException Excepción de entrada/salida.
     * @throws InterruptedException Excepción de interrupción.
     * @throws UnsupportedLookAndFeelException Excepción de look and feel no soportado.
     * @throws ClassNotFoundException Excepción de clase no encontrada.
     * @throws InstantiationException Excepción de instanciación.
     * @throws IllegalAccessException Excepción de acceso ilegal.
     */
    public AplicacionURL() {
        super("Aplicacion URL - Jordi Sumba");

        urlField = new JTextField(30);
        outputArea = new JTextArea(40, 70);
        limpiarButton = new JButton("Limpiar");
        cancelarButton = new JButton("Cancelar");
        aceptarButton = new JButton("Aceptar");
        ayudaButton = new JButton("Ayuda");
        autorLabel = new JLabel("Autor: " + AUTOR);
        pingArea = new JTextArea(40, 90);
        pingArea.setEditable(false);
        pingArea.setLineWrap(true);
        pingArea.setWrapStyleWord(true);
        tracertArea = new JTextArea(40, 90);
        tracertArea.setEditable(false);
        tracertArea.setLineWrap(true);
        tracertArea.setWrapStyleWord(true);
        nslookupArea = new JTextArea(40, 90);
        nslookupArea.setEditable(false);
        nslookupArea.setLineWrap(true);
        nslookupArea.setWrapStyleWord(true);
        curlArea = new JTextArea(40, 90);
        curlArea.setEditable(false);
        curlArea.setLineWrap(true);
        curlArea.setWrapStyleWord(true);
        telnetArea = new JTextArea(40, 90);
        telnetArea.setEditable(false);
        telnetArea.setLineWrap(true);
        telnetArea.setWrapStyleWord(true);
        tabbedPane = new JTabbedPane();


        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);
        JMenu menuVista = new JMenu("Vista");
        mb.add(menuVista);
        JMenu menu1 = new JMenu("Tamaño");
        menuVista.add(menu1);
        JMenuItem item1 = new JMenuItem("Pequeño");
        JMenuItem item2 = new JMenuItem("Mediano");
        JMenuItem item3 = new JMenuItem("Grande");
        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);
        JMenuItem fullScreen = new JMenuItem("Pantalla Completa");
        menuVista.add(fullScreen);

        /**
         * Acciones de los botones de la barra de menú.
         */
        fullScreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        item1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSize(600, 450);
            }
        });
        item2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSize(900, 600);
            }
        });
        item3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSize(1200, 900);
            }
        });
        JMenu menu2 = new JMenu("Extra");
        mb.add(menu2);
        JMenuItem acercaDe = new JMenuItem("Acerca de");
        menu2.add(acercaDe);

        /**
         * Acción del botón de acerca de.
         */
        acercaDe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarAcercaDe();
            }
        });

        JPanel urlPanel = new JPanel();
        urlPanel.add(new JLabel("URL: "));
        urlPanel.add(urlField);
        urlPanel.add(limpiarButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ayudaButton);
        buttonPanel.add(cancelarButton);
        buttonPanel.add(aceptarButton);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        panelPing = new JPanel();
        JScrollPane scrollPanePing = new JScrollPane(pingArea);
        scrollPanePing.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanePing.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelPing.add(scrollPanePing);

        panelTracert = new JPanel();
        JScrollPane scrollPaneTracert = new JScrollPane(tracertArea);
        scrollPaneTracert.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneTracert.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelTracert.add(scrollPaneTracert);

        panelNslookup = new JPanel();
        JScrollPane scrollPaneNslookup = new JScrollPane(nslookupArea);
        scrollPaneNslookup.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneNslookup.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelNslookup.add(scrollPaneNslookup);

        panelCurl = new JPanel();
        JScrollPane scrollPaneCurl = new JScrollPane(curlArea);
        scrollPaneCurl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneCurl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelCurl.add(scrollPaneCurl);

        panelTelnet = new JPanel();
        JScrollPane scrollPaneTelnet = new JScrollPane(telnetArea);
        scrollPaneTelnet.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneTelnet.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelTelnet.add(scrollPaneTelnet);

        tabbedPane.addTab("Ping", panelPing);
        tabbedPane.setToolTipTextAt(0, "Muestra el resultado del comando ping.");
        tabbedPane.addTab("Tracert", panelTracert);
        tabbedPane.setToolTipTextAt(1, "Muestra el resultado del comando tracert.");
        tabbedPane.addTab("Nslookup", panelNslookup);
        tabbedPane.setToolTipTextAt(2, "Muestra el resultado del comando nslookup.");
        tabbedPane.addTab("Curl", panelCurl);
        tabbedPane.setToolTipTextAt(3, "Muestra el resultado del comando curl.");
        tabbedPane.addTab("Telnet", panelTelnet);
        tabbedPane.setToolTipTextAt(4, "Muestra el resultado del comando telnet.");

        getContentPane().add(urlPanel, BorderLayout.NORTH);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.PAGE_END);

        limpiarButton.addActionListener(this);
        limpiarButton.setToolTipText("Limpia el area de texto actual.");
        cancelarButton.addActionListener(this);
        cancelarButton.setToolTipText("Cancela los procesos activos.");
        aceptarButton.addActionListener(this);
        aceptarButton.setToolTipText("Ejecuta todos los comandos con la url especificada.");
        ayudaButton.addActionListener(this);
        ayudaButton.setToolTipText("Abre una nueva ventana con informacion para ayudar al usuario.");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(true);
    }


    /**
     * Método que ejecuta el comando ping.
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == limpiarButton) {
            urlField.setText("");
            if (panelPing.isShowing()) {
                pingArea.setText("");
            } else if (panelTracert.isShowing()) {
                tracertArea.setText("");
            } else if (panelNslookup.isShowing()) {
                nslookupArea.setText("");
            } else if (panelCurl.isShowing()) {
                curlArea.setText("");
            } else if (panelTelnet.isShowing()) {
                telnetArea.setText("");
            }
        } else if (e.getSource() == aceptarButton) {
            String url = urlField.getText().trim();
            if (!url.isEmpty()) {
                ejecutarComando("ping " + url, pingArea);
                ejecutarComando("curl " + url, curlArea);
                //ejecutarTelnet(url, 80, telnetArea);
                ejecutarTelnet(url, 80, telnetArea);
                ejecutarComando("nslookup " + url, nslookupArea);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                ejecutarComando("nslookup -type=soa " + url, nslookupArea);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            process = Runtime.getRuntime().exec("tracert" + " -d" + " -w" + " 5 " + url);
                            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String linea;
                            tracertArea.append("Hecho por Jordi Sumba\n\n");
                            tracertArea.append("Comando: tracert -d -w 5 " + url + "\n");
                            while ((linea = br.readLine()) != null) {
                                tracertArea.append(linea + "\n");
                            }
                            tracertArea.append("\n");
                            tracertArea.setCaretPosition(tracertArea.getDocument().getLength());
                            process.waitFor();
                        } catch (IOException | InterruptedException ex) {
                            tracertArea.append("Error al ejecutar el comando: " + ex.getMessage() + "\n\n");
                            tracertArea.setCaretPosition(tracertArea.getDocument().getLength());
                        }
                    }
                }).start();
            }
        } else if (e.getSource() == ayudaButton) {
            mostrarAyuda();
        } else if (e.getSource() == cancelarButton) {
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     * Método con el que se ejecutan los comandos de manera multihilo.
     * @param comando Comando a ejecutar.
     * @param outputArea Area de texto donde se muestra el resultado del comando.
     */
    private void ejecutarComando(String comando, JTextArea outputArea) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    outputArea.append("Hecho por: Jordi Sumba\n\n");
                    outputArea.append("Comando: " + comando + "\n\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());

                    process = Runtime.getRuntime().exec(comando);
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        outputArea.append(linea + "\n");
                    }
                    outputArea.append("\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                } catch (IOException ex) {
                    outputArea.append("Error al ejecutar el comando: " + ex.getMessage() + "\n\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                }
            }
        }).start();
    }


    /**
     * Método con el que se ejecuta el comando telnet de manera multihilo.
     */
    public class TelnetThread extends Thread {
        private String url;
        private JTextArea outputArea;

        public TelnetThread(String url, JTextArea outputArea) {
            this.url = url;
            this.outputArea = outputArea;
        }

        public void run() {
            String info = "";
            String[] cmd = {"cmd", "/C", "start", "/WAIT", "C:/Windows/system32/Telnet.exe", "-f", "C:/Windows/Temp/salidaTelnet.txt", url, "80"};
            try {
                Process p = Runtime.getRuntime().exec(cmd);
                File file = new File("C:/Windows/Temp/salidaTelnet.txt");
                file.createNewFile();
                BufferedReader br = new BufferedReader(new FileReader(file));
                String linea;
                while ((linea = br.readLine()) != null) {
                    outputArea.append(linea + "\n");
                }
                outputArea.append("\n");
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputArea.append(info);
        }
    }

    /**
     * Método con el que se ejecuta el comando telnet de manera multihilo.
     * @param url URL a la que se va a hacer la conexión.
     * @param port Puerto al que se va a hacer la conexión.
     * @param outputArea Area de texto donde se muestra el resultado del comando.
     */
    public void ejecutarTelnet(String url, int port, JTextArea outputArea) {
        TelnetThread telnetThread = new TelnetThread(url, outputArea);
        telnetThread.start();
    }

    /**
     * Método con el que se muestra la ventana de Acerca de.
     * @see JFrame
     * @see JLabel
     * @see JButton
     * @see JPanel
     */
    private void mostrarAyuda() {
        JFrame ventanaAyuda = new JFrame("Ayuda");
        ventanaAyuda.setSize(600, 400);
        ventanaAyuda.setLocationRelativeTo(this);
        ventanaAyuda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton acercaDeButton = new JButton("Acerca de");
        acercaDeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarAcercaDe();
            }
        });
        JTabbedPane pestañasAyuda = new JTabbedPane();

        JPanel panelPing = new JPanel();
        panelPing.setLayout(new BoxLayout(panelPing, BoxLayout.Y_AXIS));
        panelPing.add(new JLabel("Comando Ping"));
        panelPing.add(new JLabel("Este comando permite verificar la conectividad con un host remoto."));
        panelPing.add(new JLabel("Ejemplo: ping www.google.com"));
        pestañasAyuda.addTab("Ping", panelPing);

        JPanel panelTracert = new JPanel();
        panelTracert.setLayout(new BoxLayout(panelTracert, BoxLayout.Y_AXIS));
        panelTracert.add(new JLabel("Comando Tracert"));
        panelTracert.add(new JLabel("Este comando muestra la ruta que sigue un paquete de red hasta un host remoto."));
        panelTracert.add(new JLabel("Ejemplo: tracert www.google.com"));
        pestañasAyuda.addTab("Tracert", panelTracert);

        JPanel panelNslookup = new JPanel();
        panelNslookup.setLayout(new BoxLayout(panelNslookup, BoxLayout.Y_AXIS));
        panelNslookup.add(new JLabel("Comando Nslookup"));
        panelNslookup.add(new JLabel("Este comando permite consultar los registros DNS de un dominio."));
        panelNslookup.add(new JLabel("Ejemplo: nslookup www.google.com"));
        pestañasAyuda.addTab("Nslookup", panelNslookup);

        JPanel panelCurl = new JPanel();
        panelCurl.setLayout(new BoxLayout(panelCurl, BoxLayout.Y_AXIS));
        panelCurl.add(new JLabel("Comando Curl"));
        panelCurl.add(new JLabel("Este comando permite realizar peticiones HTTP a un servidor web."));
        panelCurl.add(new JLabel("Ejemplo: curl www.google.com"));
        pestañasAyuda.addTab("Curl", panelCurl);

        JPanel panelTelnet = new JPanel();
        panelTelnet.setLayout(new BoxLayout(panelTelnet, BoxLayout.Y_AXIS));
        panelTelnet.add(new JLabel("Comando Telnet"));
        panelTelnet.add(new JLabel("Este comando permite conectarse a un servidor remoto utilizando el protocolo Telnet."));
        panelTelnet.add(new JLabel("Ejemplo: telnet www.google.com 80"));
        pestañasAyuda.addTab("Telnet", panelTelnet);

        ventanaAyuda.getContentPane().add(pestañasAyuda);
        ventanaAyuda.add(acercaDeButton, BorderLayout.PAGE_END);
        ventanaAyuda.setVisible(true);

    }

    /**
     * Método con el que se muestra la ventana de Acerca de.
     * @see JOptionPane
     * @see String
     */
    private void mostrarAcercaDe() {
        final String autor = "Jordi Sumba";
        final String nombreApp = "Aplicacion URL";
        final String version = "1.0";
        final String copyright = "© 2023 Jordi Sumba";
        String acercaDe = nombreApp + " - Versión " + version + "\n\n"
                + copyright + "\n\n"
                + "Creado por " + autor;

        JOptionPane.showMessageDialog(this, acercaDe, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Metodo en el que se ejecuta la aplicación.
     * @param args Argumentos de la aplicación.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AplicacionURL().setVisible(true);
            }
        });
    }
}
