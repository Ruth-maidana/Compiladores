/**
 *
 * @author Ruth
 */
public class main {

    public main() {
        // inicializamos la entrada
        String entrada =  "{  \n" +
        "   \"personas\" : [  \n" +
        "      {  \n" +
        "         \"ci\" : 123457 , \n" +
        "         \"nombre\" : JulioPerez , \n"+
        "         \"casado\" : false ,\n"+
        "         \"hijos\" : [ \n" +
        "          ] \n"+
        "      },  \n"+
        "      {  \n" +
        "         \"ci\" : 7654321 , \n" +
        "         \"nombre\" : JuanGómez , \n"+
        "         \"casado\" : true ,\n"+
        "         \"hijos\" : [ \n" +   
        "           {  \n" +
        "               \"nombre\" : Jorge , \n"+
        "               \"edad\" : 18 \n"+        
        "           },  \n"+
        "           {  \n" +
        "               \"nombre\" : Valeria , \n"+
        "               \"edad\" : 16 \n"+        
        "           }  \n"+
        "         ]  \n"+
        "     }  \n"+
        "   ]  \n"+
        " }  \n";
        lexico analizadorLex= new lexico();//objeto para realizar el analisis lexico
        char token[];
        int band=0;
        token = new char[entrada.length()]; //array de caracteres para guardar tokens detectados
        int i=0,j=0;
         
        for(i=0; i< entrada.length(); i++ ){
        
             //detecta caracteres limitadores, cuando detecta un caracter delimitador
             //guarda los tokens para verificar con la funcion si es valido o no
            if(analizadorLex.limitador(entrada.charAt(i))==0){
                  
                //bandera controla cuando existe mas caracteres limitadores
                //cuando ya no hay caracter limitadores
                if(band==1){
                    //se analiza la palabra guardada llamando a la funcion
                    analizadorLex.function(token);   
                    //luego se vacia el token para volver a guardar las palabras
                    token= null;
                    token = new char[entrada.length()];
                    j=0;       
                }
                band=0;
              
            } else {
                //mientras no contenga un limitador se guardaran las palabras
                band=1;
                token[j]= entrada.charAt(i);
                j++;
            }
        }
        System.out.println(analizadorLex.getSalida());   
    }

    public static void main(String[] args) {
        // TODO code application logic here
        new main(); 
    } 
}
