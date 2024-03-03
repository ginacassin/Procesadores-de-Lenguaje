package alex;

import errors.GestionErroresTiny;

%%
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode
%public
%cup

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }

%}

%eofval{ //valor que se devuelve al llegar a final de fichero
  return ops.unidadEof();
%eofval}

%init{ //codigo que se incluye en el constructor
  ops = new ALexOperations(this);
%init}

//EXPRESIONES REGULARES
//definiciones auxiliares
letra  = ([A-Z]|[a-z])
digito = [0-9]
digitoSinCero = [1-9]
parteEntera = ({digitoSinCero}{digito}*)|0
parteDecimal = ({digito}*{digitoSinCero})|0
//definiciones lexicas
suma = \+
resta = \-
mul = \*
div = \/
parentesisAbrir = \(
parentesisCerrar = \)
abrirBloque = \{
cerrarBloque = \}
tamanoAbrir = \[
tamanoCerrar = \]
finDeclaraciones = &&
asignacion = \=
menor = <
mayor = >
menorIgual = <\=
mayorIgual = >\=
igual = \=\=
noIgual = \!\=
and = (a|A)(n|N)(d|D)
or = (o|O)(r|R)
not = (n|N)(o|O)(t|T)
true = (t|T)(r|R)(u|U)(e|E)
false = (f|F)(a|A)(l|L)(s|S)(e|E)
modulo = %
puntero = \^
bitwiseAnd = &
tipoEntero = (i|I)(n|N)(t|T)
tipoReal = (r|R)(e|E)(a|A)(l|L)
tipoBooleano = (b|B)(o|O)(o|O)(l|L)
tipoString = (s|S)(t|T)(r|R)(i|I)(n|N)(g|G)
null = (n|N)(u|U)(l|L)(l|L)
proc = (p|P)(r|R)(o|O)(c|C)
if = (i|I)(f|F)
else = (e|E)(l|L)(s|S)(e|E)
while = (w|W)(h|H)(i|I)(l|L)(e|E)
struct = (s|S)(t|T)(r|R)(u|U)(c|C)(t|T)
new = (n|N)(e|E)(w|W)
delete = (d|D)(e|E)(l|L)(e|E)(t|T)(e|E)
read = (r|R)(e|E)(a|A)(d|D)
write = (w|W)(r|R)(i|I)(t|T)(e|E)
nl = (n|N)(l|L)
type = (t|T)(y|Y)(p|P)(e|E)
call = (c|C)(a|A)(l|L)(l|L)
eval = @
punto = \.
coma = \,
puntoYComa = \;
identificador = ({letra}|_)({letra}|{digito}|_)*
literalEntero = (\+|\-)?{parteEntera}
literalReal = (\+|\-)?{parteEntera}((\.{parteDecimal})|((e|E)(\+|\-)?{parteEntera})|((\.{parteDecimal})((e|E)(\+|\-)?{parteEntera})))
literalCadena = \"[^\"]*\"
//definicion de cadenas ignorables
separador = [ \t\r\b\n]
comentario = ##([^\n])*
%%
//descripcion del programa de procesamiento
{separador}               {}
{comentario}              {}
{suma}                    {return ops.unidadSuma();}
{resta}                   {return ops.unidadResta();}
{mul}                     {return ops.unidadMul();}
{div}                     {return ops.unidadDiv();}
{parentesisAbrir}         {return ops.unidadParentesisAbrir();}
{parentesisCerrar}        {return ops.unidadParentesisCerrar();}
{abrirBloque}             {return ops.unidadAbrirBloque();}
{cerrarBloque}            {return ops.unidadCerrarBloque();}
{tamanoAbrir}             {return ops.unidadTamanoAbrir();}
{tamanoCerrar}            {return ops.unidadTamanoCerrar();}
{finDeclaraciones}        {return ops.unidadFinDeclaraciones();}
{asignacion}              {return ops.unidadAsignacion();}
{menor}                   {return ops.unidadMenor();}
{mayor}                   {return ops.unidadMayor();}
{menorIgual}              {return ops.unidadMenorIgual();}
{mayorIgual}              {return ops.unidadMayorIgual();}
{igual}                   {return ops.unidadIgual();}
{noIgual}                 {return ops.unidadNoIgual();}
{and}                     {return ops.unidadAnd();}
{or}                      {return ops.unidadOr();}
{not}                     {return ops.unidadNot();}
{true}                    {return ops.unidadTrue();}
{false}                   {return ops.unidadFalse();}
{modulo}                  {return ops.unidadModulo();}
{puntero}                 {return ops.unidadPuntero();}
{bitwiseAnd}              {return ops.unidadBitwiseAnd();}
{tipoEntero}              {return ops.unidadTipoEntero();}
{tipoReal}                {return ops.unidadTipoReal();}
{tipoBooleano}            {return ops.unidadTipoBooleano();}
{tipoString}              {return ops.unidadTipoString();}
{null}                    {return ops.unidadNull();}
{proc}                    {return ops.unidadProc();}
{if}                      {return ops.unidadIf();}
{else}                    {return ops.unidadElse();}
{while}                   {return ops.unidadWhile();}
{struct}                  {return ops.unidadStruct();}
{new}                     {return ops.unidadNew();}
{delete}                  {return ops.unidadDelete();}
{read}                    {return ops.unidadRead();}
{write}                   {return ops.unidadWrite();}
{nl}                      {return ops.unidadNl();}
{type}                    {return ops.unidadType();}
{call}                    {return ops.unidadCall();}
{eval}                    {return ops.unidadEval();}
{punto}                   {return ops.unidadPunto();}
{coma}                    {return ops.unidadComa();}
{puntoYComa}              {return ops.unidadPuntoYComa();}
{identificador}           {return ops.unidadIdentificador();}
{literalEntero}           {return ops.unidadLiteralEntero();}
{literalReal}             {return ops.unidadLiteralReal();}
{literalCadena}           {return ops.unidadLiteralCadena();}
[^]                       {ops.error();}