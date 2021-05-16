import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ruth
 */
public class main {
    lexema analizadorLex;
    String output= "";
    String input =  "";
    int c=0;
    

    public main() throws IOException {
       //Llama a la funcion de lectura para leer las entrada de datos del archivo fuente
       input=lectura();
       analizadorLex = new lexema(input,c);
       //Imprime texto de entrada
       System.out.println(input); 
       //Trae el token a ser verificado
       analizadorLex.getToken(); 
       
       while(analizadorLex.getTokens() == " " || analizadorLex.getTokens() == "\n" || analizadorLex.getTokens() == "\t"   ){ 
           analizadorLex.getToken();
       }
       analisis();
       
       //Se imprime en pantalla la traduccion
       System.out.println("Se muestra en pantalla el sgte archivo de salida");
       System.out.println(output);
       
       //Si desea crear el archivo de salida debe habilitar la llamada a generarSalida y modificar la ruta en la funcion
       //generarSalida(output);
       
    }

    public void analisis() throws IOException{
        json();
    }
       
    public void json() throws IOException{
        String[] primero={"L_CORCHETE", "L_LLAVE"};
        String[] siguiente={""};
        controlEntrada (primero,siguiente);
        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) {
           elemento(); 
        }
        controlEntrada(siguiente,primero);
    }
       
    public void elemento(){
        String[] primero={"L_CORCHETE", "L_LLAVE"};
        String[] siguiente={"COMA","",""};
        controlEntrada (primero,siguiente);
           
        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) {   
            if(analizadorLex.getTokens()== "L_LLAVE"){
                objeto();  
            }else if(analizadorLex.getTokens()== "L_CORCHETE"){  
                vector();
           } 
        }
         siguiente[1]= "R_CORCHETE";
         siguiente[2]= "R_LLAVE";
         controlEntrada(siguiente,primero);
    }
            
    public void vector(){
        String[] primero={"L_CORCHETE"};
        String[] siguiente={"COMA",""};
        controlEntrada (primero,siguiente);
        String[] traduccion =  {""};

        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) {  
           if(analizadorLex.getTokens()== "L_CORCHETE"){
                match("L_CORCHETE");
                if(analizadorLex.getTokens()!= "R_CORCHETE") {
                    ListaElementos();  
                }
            match("R_CORCHETE");
            }
        }
        siguiente[1]= "R_LLAVE";
        controlEntrada(siguiente,primero);    
    }
       
    public void ListaElementos(){
        String[] primero={"L_CORCHETE","L_LLAVE"};
        String[] siguiente={"R_CORCHETE"};
        controlEntrada (primero,siguiente);
        String[] traduccion =  {"<item>","","</item>",""};
   
        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) {  
            elemento();
            traduccion[1] = output;
            output = "";
            if(analizadorLex.getTokens()== "COMA")  {
                ListaElementoPrimero();
                traduccion[3]=output;
                output="";
                for (int i = 0; i < 4; i++) { 
                    output = output + traduccion[i];   
                }
            }
        }
        controlEntrada(siguiente,primero);
    }  
            
    public void ListaElementoPrimero(){
        String[] primero={"COMA"};
        String[] siguiente={"R_CORCHETE"};
        controlEntrada (primero,siguiente);
        String[] traduccion =  {"<item>","","</item>",""};
        
        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) {   
            if(analizadorLex.getTokens()=="COMA" ){
                match("COMA");
                traduccion[3] = traduccion[3] + output;
                elemento();
                traduccion[1] = output;
                output = "";
                for (int i = 0; i < 4; i++) {
                    output = output + traduccion[i];    
                }
                if(analizadorLex.getTokens()== "COMA")  { 
                  ListaElementoPrimero();
                }
            }
        } 
        controlEntrada(siguiente,primero);    
    }        
       
    public void objeto(){
        String[] primero={"L_LLAVE"};
        String[] siguiente={"COMA",""};
        controlEntrada (primero,siguiente);
        
        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) {  
           if(analizadorLex.getTokens()== "L_LLAVE"){  
              match("L_LLAVE");
              if(analizadorLex.getTokens()!= "R_CORCHETE") {
                ListaAtributos();           
              }
              match("R_LLAVE");
            }
        }  
        siguiente[1]= "R_CORCHETE";
        controlEntrada(siguiente,primero);      
    }
          
    public void ListaAtributos(){
        String[] primero={"STRING"};
        String[] siguiente={"R_LLAVE"};
        controlEntrada (primero,siguiente);
        
        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) {     
            Atributos();
            if(analizadorLex.getTokens()== "COMA")  {
                ListaAtributoPrimero();
            }
        }
        controlEntrada(siguiente,primero);
    } 
          
    public void ListaAtributoPrimero(){ 
        String[] primero={"COMA"};
        String[] siguiente={"R_LLAVE"}; 
        controlEntrada (primero,siguiente);
        String[] traduccion =  {""};
  
        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) {   
            if(analizadorLex.getTokens()== "COMA")  {
                match("COMA");  
                traduccion[0] = traduccion[0] + output;
                Atributos();
                traduccion[0] = traduccion[0] + output +"\n";
                output = "";
                if(analizadorLex.getTokens()== "COMA")  {
                    ListaAtributoPrimero();
                    traduccion[0] = traduccion[0] + output + "\n"; 
                }
            }
        }
        controlEntrada(siguiente,primero);
        output = traduccion[0];
    }  
    
    //verifica el sgte siguiente
    public void Atributos(){ 
        String[] primero={"STRING"};
        String[] siguiente={"COMA","R_LLAVE"};
        controlEntrada (primero,siguiente);
        String[] traduccion =  {"<","",">","","<","/","",">"};
        
        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) { 
          NombreAtributo();
          traduccion[1] = output;
          traduccion[6] = output;
          output="";
          match("DOS_PUNTOS");
          ValorAtributo();
          traduccion[3] = output;
          output = "";
          
            for (int i = 0; i < 8; i++) {
                output = output + traduccion[i];   
            }
        } 
        controlEntrada(siguiente,primero); 
    }  
    
    public void NombreAtributo(){
        String[] primero={"STRING"};
        String[] siguiente={"DOS_PUNTOS",""};
        String[] traduccion =  {""};
        controlEntrada (primero,siguiente);

        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) {    
            if (analizadorLex.getTokens()== "STRING"){ 
                traduccion[0]= analizadorLex.getPal().replace('"','\0');
                match("STRING");
                output = traduccion[0];
            }
        }  
        siguiente[1]= "R_LLAVE";
        controlEntrada(siguiente,primero); 
    }

    public void ValorAtributo(){
        String[] primero={"PR_FALSE","PR_NULL","PR_TRUE","NUMBER","L_CORCHETE","L_LLAVE","DOS_PUNTOS","STRING"};
        String[] siguiente={"COMA",""};
        String[] atributo_traduccion =  {""};
        controlEntrada (primero,siguiente);

        if (TokenEnVector(analizadorLex.getTokens(),siguiente )==1) { 
           if(analizadorLex.getTokens()== "L_LLAVE" || analizadorLex.getTokens()== "L_CORCHETE" ){
               elemento();
           }   
           if(analizadorLex.getTokens()== "NUMBER"){
               atributo_traduccion[0]= analizadorLex.getPal();
               match("NUMBER");
               output= atributo_traduccion[0];
           }else if(analizadorLex.getTokens()== "STRING"){
               atributo_traduccion[0]= analizadorLex.getPal();
               match("STRING");
               output= atributo_traduccion[0];
           }else if(analizadorLex.getTokens()== "PR_TRUE"){
               atributo_traduccion[0]= analizadorLex.getPal();
               match("PR_TRUE");
               output= atributo_traduccion[0];
           }else if(analizadorLex.getTokens()== "PR_FALSE"){
               atributo_traduccion[0]= analizadorLex.getPal();
               match("PR_FALSE");
               output= atributo_traduccion[0];
           }else if(analizadorLex.getTokens()== "PR_NULL"){
               atributo_traduccion[0]= analizadorLex.getPal();
               match("PR_NULL");
               output= atributo_traduccion[0];
           }
        }
        siguiente[1]= "R_LLAVE";
        controlEntrada(siguiente,primero);
    }
    
    //Controla los espacios en blanco y saltos de lineas
    public void match(String exptoken){
        while(analizadorLex.getTokens() == " " || analizadorLex.getTokens() == "\n" || analizadorLex.getTokens() == "\t"   ){
           analizadorLex.getToken();
        }    
       if(analizadorLex.getTokens() == exptoken){
           System.out.println(analizadorLex.getTokens() + "  sintacticamente correcto"); //imprime tokens que estan sintacticamente correctos
           analizadorLex.getToken();    
       }
        while(analizadorLex.getTokens() == " " || analizadorLex.getTokens() == "\n" || analizadorLex.getTokens() == "\t"  ){           
           analizadorLex.getToken();
       }   
    }
    
    //Verificara la entrada si llega a EOF se detiene
    public void controlEntrada(String[] primero, String[] siguiente){
        if(analizadorLex.getTam()< input.length()){  
           if(TokenEnVector(analizadorLex.getTokens(),primero )==1 )  {
               System.out.println("error sintactico token" + analizadorLex.getTokens() + "no valido" );    
               controlError(ConcatenarVector(primero,siguiente));     
           }
        }
    }
    
    //Para el control de errores
    public void controlError(String[] sync){        
        while(TokenEnVector(analizadorLex.getTokens(),sync )==1){
            if(analizadorLex.getTam()< input.length()){ break;}
                analizadorLex.getToken();
                System.out.println("Error");    
        }
    }
    
    public String lectura(){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String lineas= "";
        try {
                //Se abre el archivo fuente para su lectura
		archivo = new File ("fuente.txt");
		fr = new FileReader (archivo);
		br = new BufferedReader(fr);
		// Lectura del fichero
		System.out.println("Se esta leyendo el fuente.txt");
		String linea;
		while((linea=br.readLine())!=null){
                    lineas = lineas + linea; }
                    return lineas;
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           try{
              if( null != fr ){
                 fr.close();
              }
           }catch (Exception e2){
              e2.printStackTrace();
           }
        }
        return null;
    }
    
    
    public void generarSalida(String input) throws IOException {
        File archivo = new File("C:\\Users\\Usuario\\Documents\\Facultad\\1erSemestre_2021\\Compiladores","output.xml");
        try{
            // Si el archivo no existe es creado
            if (!archivo.exists()) {
                archivo.createNewFile();               
                System.out.println("El fichero se ha creado correctamente");
            }else{
                System.out.println("No ha podido ser creado el fichero");
            }
            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(fw);   
            bw.write(input);
            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
    //Concatena los vectores de string
    public static String[] ConcatenarVector(String[] vector1, String[] vector2){
	String[] ret = new String[vector1.length + vector2.length];
	System.arraycopy(vector1, 0, ret, 0, vector1.length);
	System.arraycopy(vector2, 0, ret, vector1.length, vector2.length); 
	return ret;
    } 
     
    //Controla si un token esta en un array o no
    public static int TokenEnVector(String token,String[] array){
        for(int i=0;i< array.length; i++){
            if(token == array[i] ){         
            return 0;
            }
        }
        return 1;
    }
    
    public static void main(String[] args) throws IOException {
        new main(); 
    }
}