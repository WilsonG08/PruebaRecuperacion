import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ventanaFecha {
    private JPanel panel1;
    private JComboBox comboDia;
    private JComboBox comboMes;
    private JComboBox comboAnio;
    private JButton guardarButton;

    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    Connection con;

    public ventanaFecha(){

        //String[] mes = new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};


        //COMBO BOX DIA
        try{
            con = getConection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT Dia_F FROM DIAF");

            while(rs.next()){
                comboDia.addItem(rs.getString("Dia_F"));
            }

        }catch (HeadlessException | SQLException w){
            System.out.println(w);
        }

        //COMBO BOX MES
        try{
            con = getConection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT Mes_F FROM MESF");

            while(rs.next()){
                comboMes.addItem(rs.getString("Mes_F"));
            }

        }catch (HeadlessException | SQLException w){
            System.out.println(w);
        }

        //COMBO BOX AÑO
        try{
            con = getConection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT Anio_F FROM ANIOF");

            while(rs.next()){
                comboAnio.addItem(rs.getString("Anio_F"));
            }

        }catch (HeadlessException | SQLException w){
            System.out.println(w);
        }


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con = getConection();
                String dia = comboDia.getSelectedItem().toString();
                String mes = comboMes.getSelectedItem().toString();
                String anioo = comboAnio.getSelectedItem().toString();

                try{
                    ps = con.prepareStatement("INSERT INTO FORMATO (DA_F, MS_F, AN_F) VALUES (?,?,?)");

                    ps.setString(1,dia);
                    ps.setString(2,mes);
                    ps.setString(3,anioo);


                    int rest = ps.executeUpdate();

                    if(rest > 0){
                        JOptionPane.showMessageDialog(null,"FECHA GUARDADA");
                    }else{
                        JOptionPane.showMessageDialog(null,"ERROR!");
                    }

                }catch (HeadlessException | SQLException w){
                    System.out.println(w);
                }
            }
        });
    }



    public static Connection getConection(){
        Connection co = null;
        String base = "FECHA";
        String url = "jdbc:mysql://localhost:3306/"+base;
        String user = "root";
        String pasword = "Wilson08";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            co = DriverManager.getConnection(url,user,pasword);

        }catch (ClassCastException | SQLException w){
            System.out.println(w);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return co;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Nose");
        frame.setContentPane(new ventanaFecha().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400,400);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}



/*

public int convierteMes(Object seleccion) {
    if (seleccion instanceof String) {
        String mesSeleccionado = (String)seleccion;
        for (int mes = 0; mes < arrayMeses.length; mes++) {
            if (mesSeleccionado.equals(arrayMeses[mes])) {
                return mes;
               //puedes retornar mes+1 si quieres que enero sea uno
               //pero usualmente enero se toma como cero,...
            }
        }
    }
    return -1;
}
*/
