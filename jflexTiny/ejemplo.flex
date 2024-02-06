package alex;

%%
//configuracion del proceso de generacion
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode

//codigo que se incluye en la clase generada
%{
  private ALexOperations ops;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
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
digitoSinCero = [1-9]
digito = [0-9]
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
noIgual = !\=
and = and
or = or
not = not
true = true
false = false
modulo = %
puntero = \^
bitwiseAnd = &
tipoEntero = int
tipoReal = real
tipoBooleano = bool
tipoString = string
tipoArray = array
creacionTipo = type
null = null
procedimiento = proc
if = if
else = else
while = while
estructura = struct
new = new
delete = delete
read = read
write = write
nl = nl
call = call
eval = @
punto = \.
coma = \,
identificador = ({letra}|_)({letra}|{digito}|_)*
literalEntero = (\+|\-)?{parteEntera}
literalReal = {literalEntero}((\.{parteDecimal})((e|E){literalEntero}))|(\.{parteDecimal})|((e|E){literalEntero})
//definicion de cadenas ignorables
separador = [ \t\r\b\n]
comentario = ##([^\n,EOF])*
%%
//descripcion del programa de procesamiento
{separador}               {}
{comentario}              {}
{suma}                    {return ops.unidadSuma();}
{resta}                   {return ops.unidadResta();}
{mul}                     {return ops.unidadMul();}
{div}                     {return ops.unidadDiv();}
{parentesisAbrir}         {return ops.unidadPAp();}
{parentesisCerrar}        {return ops.unidadPCierre();}
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
{tipoArray}               {return ops.unidadTipoArray();}
{creacionTipo}            {return ops.unidadCreacionTipo();}
{null}                    {return ops.unidadNull();}
{procedimiento}           {return ops.unidadProcedimiento();}
{if}                      {return ops.unidadIf();}
{else}                    {return ops.unidadtElse();}
{while}                   {return ops.unidadWhile();}
{estructura}              {return ops.unidadEstructura();}
{new}                     {return ops.unidadNew();}
{delete}                  {return ops.unidadDelete();}
{read}                    {return ops.unidadRead();}
{write}                   {return ops.unidadWrite();}
{nl}                      {return ops.unidadNl();}
{call}                    {return ops.unidadCall();}
{eval}                    {return ops.unidadEval();}
{punto}                   {return ops.unidadPunto();}
{coma}                    {return ops.unidadComa();}
{identificador}           {return ops.unidadId();}
{literalEntero}           {return ops.unidadEnt();}
{literalReal}             {return ops.unidadReal();}
[^]                       {ops.error();}