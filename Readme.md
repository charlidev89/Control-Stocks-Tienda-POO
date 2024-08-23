# Organización del proyecto:
- La organizacion del proyecto es por dominio

# Diagrama UML:
- se realizó un diagrama en UML para mostrar las relaciones entre las clases, el archivo se encuentra en el proyecto en PDF , se encuentra en la carpeta SRC

# Interpretación del enunciado y aplicación:
- 1) se interpreta  la clase  **Envasado**  como a todas sus instancias son productos comestibles,  ya que en el enunciado no lo especifica.
- se realiza una subclase  Abstracta  **ProductoComestible** que hereda de la superclase **Producto**  ya que  la clase Bebida y 
  Envasado compartían un atributo  ---> "importado:  boolean"  y además ambas son comestibles.
 
- 2)se agrega los atributos  caloría y fechaDeVencimiento en la clase **ProductoComestible** 
  la clase **Limpieza** hereda de la superClase **Producto**

- 3)se realiza una clase extra **Venta** que mejora la  encapsulación: La tienda ahora es responsable de mostrar su propio estado.
  optimiza el código más limpio en la clase de prueba.
  Es mass fácil de mantener: Si cambia la forma de mostrar el estado, solo modificamos la clase Tienda.
  nos aseguramos que el estado de la tienda siempre se muestre de la misma manera en toda la aplicación.
- 
# implementacion personal:
- el atributo disponible booleano se cambia automaticamente si el producto ha vencido , para ello  se implementó un metodo en el constructor
 que compara la fecha del producto con la fecha actual,  el producto solo puede ser vendido si esta disponible osea si no ha vencido.
- venta de productos:  en la salida por pantalla de la venta de producto se agrego la opcion de mostrar ganancia real segun el porcentaje de ganancia del producto.
# Estructuras de datos y diseño:

- se decide utilizar a ArrayList como estructura de dato , HashMap y aplicamos concepto de Stream y collectors para ordenar las listas
  Además se agregó una clase **Venta** que tiene toda la logica de realizar las ventas con la idea de que si se desea implementar
  otra seccion de la tienda como Carniceria o Verduleria sea escalable, al igual que implementacion  de una interfaz Comestible  por si a futuro se desea agregar otro tipo de productos o metodos compartidos.

# Implementación:
- se implementa y ejecuta todo en la clase Test que es donde se encuentra el método main y
  se crea los objetos necesarios para mostrar el funcionamiento , ya que el enunciado no pedía utilizar TestUnitarios.
- En la clase test, se crea distintos objetos y metodos estaticos para ir mostrando la funcionalidad de la tienda.
- Se ha agregado varios comentarios para ir siguiendo la lógica del código.
