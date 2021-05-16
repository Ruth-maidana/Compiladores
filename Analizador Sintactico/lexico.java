/**
 *
 * @author Ruth
 */
public class lexico {
    
    String salida="";
    String tokens="";
    String entrada="";
    String pl="";//string 
    String[] simbolos = {"L_CORCHETE","R_CORCHETE","L_LLAVE","R_LLAVE","COMA","DOS_PUNTOS"," ","\n"};
    //lexemas
    char[] lex = {'[',']','{','}',',',':',' ','\n'};
    String[] comp ={"PR_TRUE","PR_FALSE","PR_NULL","STRING","NUMBER","PALABRA NO VALIDA ERROR LEXICO"};
    //Estado inicial 5 para ver si hay alguna expresion que no pertenece a los tokens
    int state=5; 
    
    //Identifica los simbolos delimitadores para ER
    public int limitador(char l){
        for (int i=0;i<simbolos.length;i++){  
            if(l == lex[i] ){
                salida = salida + simbolos[i] +" ";
                tokens= simbolos[i];
                return 0;
            }
        }
        return 1;
    }  
  
    //Identifica simbolos que no forman parte de la ER
    public int ControlLimitador(char l){
        for (int i=0;i<simbolos.length;i++){
            if(l == lex[i] ){
                salida = salida + simbolos[i] +" ";
                return 0;
            }
        }
        return 1;
    }
    
    //Identifica las ER
    public void detectarExpReg(char[] l){
        for(int i=0;i < l.length;i++){
            //Controla si es verdadero
            if(l[i]== 't'){
                i++;
                
                if(l[i]== 'r'){
                    i++;
                }else{  System.out.println("error");   }
                
                if(l[i]== 'u'){
                    ; 
                    i++;
                }
                
                if(l[i]== 'e'){
                    state=0; 
                    i++;
                }

            //Controla si es falso
            }else if(l[i]=='f'){
                i++;
 
                if(l[i]== 'a'){
                    i++;
                }
                
                if(l[i]== 'l'){
                    i++;
                } 

                if(l[i]== 's'){
                    i++;
                } 

                if(l[i]== 'e'){
                    i++;
                    state=1;

                }
                
            //Controla si existen nulos   
            }else if(l[i]=='n'){
                i++;
                if(l[i]=='u'){
                    i++;
                }
                if(l[i]=='l'){
                    i++;
                    if(l[i]=='l'){
                        i++;
                        state=2;
                    }
                }
                    
            //Controla la entrada de Strings
            }else if(l[i]=='"'){
                i++;
                while(l[i]!='"'  ){
                        i++;
                }
                if(l[i]=='"'){
                    state=3;
                }

            //Controla la ER 
            }else if( entradaNumero(l[i])==1  ){
                i++;
                state=4;
                     
                if(l[i]=='.'){
                    state=4;
                    i++;
                        
                    if(entradaNumero(l[i])==1){ state=4; } 
                    else{ state=5; }           
                }
                      
                if(l[i]=='e' || l[i]=='E'){
                    state=4;
                    i++;
                        
                    if(entradaNumero(l[i])==1){ state=4; } 
                    else{ state=5; }
                }

            //Si la lectura de archivo ya llego a EOF
            }else if( l[i]=='e'){
                i++;
                if( l[i]=='o'){
                    i++;
                    if( l[i]=='f'){
                        i++;
                        System.exit(0);
                    }
                }
            }
        }
        salida= salida + comp[state]+" ";
        tokens=comp[state];
        pl=String.valueOf(l);
    }     
    
    //Busca si en la entrada hay numericos
    public int entradaNumero(char num){
        if(num=='0'|| num=='1'||num=='2'||num=='3'||num=='4'||num=='5'||num=='6'||num=='7'||num=='8'||num=='9' ){
        return 1; 
        }
    return 0;
   }

    public String getSalida() {        
        return salida;    
    }

    public String getTokens() {
        return tokens;
    }

    public String getPal() {
        return pl;
    }
}
