# Organización del proyecto:
- La organización del proyecto es por dominio

# Diagrama UML:
- Se realizó un diagrama en UML para mostrar las relaciones entre las clases, el archivo se encuentra en el proyecto en formato PDF y PNG  en la carpeta SRC.

# Interpretación del enunciado y aplicación:
- 1)Se interpreta  la clase  **Envasado**  como a todas sus instancias de tipo productos comestibles,  ya que en el enunciado no lo especifica.
- Se realiza una subclase  Abstracta  **ProductoComestible** que hereda de la superclase **Producto**  ya que  la clase Bebida y 
  Envasado comparten un atributo  ---> "importado:  boolean"  y además ambas son comestibles.
 
- 2)Se agrega los atributos  caloría y fechaDeVencimiento en la clase **ProductoComestible** 
  la clase **Limpieza** hereda de la superClase **Producto**

- 3)Se realiza una clase extra **Venta** que mejora la  encapsulación: La tienda ahora es responsable de mostrar su propio estado.
  optimiza el código más limpio en la clase de prueba.
  Es mas fácil de mantener: ya que Si cambia la forma de mostrar el estado, solo modificamos la clase Tienda.
  nos aseguramos que el estado de la tienda siempre se muestre de la misma manera en toda la aplicación, ademas cada producto se muestra asi mismo de esa manera modularizamos y delegamos responsabilidades mientras favorecemos el encapsulamiento.
  
# Implementación personal:
- El atributo "disponible" de tipo booleano se cambia automaticamente si el producto ha vencido , para ello  se implementó un metodo en el constructor
 que compara la fecha del producto con la fecha actual,  el producto solo puede ser vendido si esta disponible osea si no ha vencido.
- Venta de productos:  en la salida por pantalla de la venta de producto se agrego la opcion de mostrar ganancia real segun el porcentaje de ganancia del producto.

# Estructuras de datos y diseño:
- Se decide utilizar a ArrayList como estructura de dato , HashMap y aplicamos concepto de Stream y collectors para ordenar las listas.
  Además como lo mencionamos anteriormente , se agregó una clase **Venta** que tiene toda la logica de realizar las ventas con la idea de que si se desea implementar
  otra seccion de la tienda como "Carniceria o Verduleria" sea escalable, al igual que implementacion  de una interfaz Comestible  por si a futuro se desea agregar otro tipo de productos o metodos compartidos.

# Implementación y Ejecución:
- Se implementa y ejecuta todo en la clase Test que es donde se encuentra el método main y
  se crea los objetos necesarios para mostrar el funcionamiento , ya que el enunciado no pedía utilizar TestUnitarios , tampoco que venga de una BD.
- En la clase test, se crea distintos objetos y metodos estaticos para ir mostrando la funcionalidad de la tienda, asi como tambien excepciones para ir mostrando el manejo de posibles errores de ejecución.
- Ademas, Se ha agregado varios comentarios para ir siguiendo la lógica del código.
