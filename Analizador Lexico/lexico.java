/**
 *
 * @author Ruth
 */
public class lexico {
    // Representan la tabla se simbolos
    String comp[] ={"PR_TRUE","PR_FALSE","PR_NULL","STRING","NUMBER","PALABRA NO VALIDA"};
    char lex[] = {'[',']','{','}',',',':',' ','\n'};
    int estado=5; // genera palabra no valida por si exista alguna expresion que no exista en el alfabeto o no represente un token valido
    String salida="";
    String simbolos[] = {"L_CORCHETE","R_CORCHETE","L_LLAVE","R_LLAVE ","COMA","DOS_PUNTOS"," ","\n"};
    
    public int limitador(char letra){

      //esta funcion se encargara de identificar los simbolos limitadores de codigos todo simbolo que no se necesite una expresion regular amplia
        for (int i=0;i<simbolos.length;i++){
            if(letra == lex[i] ){
                salida = salida + simbolos[i] +" ";
                return 0;
            }
        }
    return 1;
    }  
    
    public int function(char letra[]){
     //detecta las expresiones regulares  
        for(int i=0;i < letra.length;i++){
            //controla true 
            if(letra[i]== 't'){
                i++;
         
                if(letra[i]== 'r'){
                    i++;
                }else{  System.out.println("ERROR");   }
                
                if(letra[i]== 'u'){ 
                    i++;
                }   
                        
                if(letra[i]== 'e'){
                    estado=0; 
                    i++;
                }
                            
                //controla false              }
            }else if(letra[i]=='f'){
                i++;
                
                if(letra[i]== 'a'){
                    i++;
                }
                    
                if(letra[i]== 'l'){
                    i++;
                } 
                       
                if(letra[i]== 's'){
                    i++;
                } 
                    
                if(letra[i]== 'e'){
                    i++;
                    estado=1;            
                }  
              //controla los nulls     
            }else if(letra[i]=='n'){
                i++; 
                
                if(letra[i]=='u'){
                      i++;
                }
                
                if(letra[i]=='l'){
                    i++;
                    
                    if(letra[i]=='l'){
                        i++;
                        estado=2;        
                    }
                }
             //controla las cadenas
            }else if(letra[i]=='"'){
                i++;
                 
                while(letra[i]!='"'){
                    i++;
                }
                
                if(letra[i]=='"'){
                    estado=3;                
                }  
             //controla la expresion regular de numeros 
            }else if( entradaNumero(letra[i])==1  ){
                i++;
                estado=4;
                   
                if(letra[i]=='.'){
                    estado=4;
                    i++;
                     
                    if(entradaNumero(letra[i])==1){ estado=4; } 
                    else{ estado=5; }           
                }
                   
                if(letra[i]=='e' || letra[i]=='E'){
                    estado=4;
                    i++;
                    
                    if(entradaNumero(letra[i])==1){ estado=4; }
                    else{ estado=5; }   
                }
        
            //controla el fin de linea
            }else if( letra[i]=='e'){
                i++;
                    
                if( letra[i]=='o'){
                    i++;
                        
                    if( letra[i]=='f'){
                        i++;
                        System.exit(0);
                    }
                }
            }
       
        }
       salida= salida + comp[estado]+" ";
      return 0;
    }     
     
   public int entradaNumero(char num){
       
   //identifica si un caracter es numerico
        if(num=='0'|| num=='1'||num=='2'||num=='3'||num=='4'||num=='5'||num=='6'||num=='7'||num=='8'||num=='9' ){
            return 1;  
        }
   return 0;
   }

    public String getSalida() {
        return salida;
    }
}
