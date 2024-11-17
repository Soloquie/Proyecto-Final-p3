<<<<<<< Updated upstream:FinalProject/src/main/java/uniquindio/finalproject/Model/Categoria.java
    package uniquindio.finalproject.Model;

    public class Categoria {
        private String idCategoria;
        private String nombre;
        private String descripcion;

        public Categoria(String idCategoria, String nombre, String descripcion) {
            this.idCategoria = idCategoria;
            this.nombre = nombre;
            this.descripcion = descripcion;
        }

        public String getIdCategoria() {
            return idCategoria;
        }

        public void setIdCategoria(String idCategoria) {
            this.idCategoria = idCategoria;
        }

        public String getNombreCategoria() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcionCategoria() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }
=======
    package uniquindio.finalproject.Model;

    import java.io.Serializable;

    public class Categoria implements Serializable {
        private static final long serialVersionUID = 1L;
        private String idCategoria;
        private String nombre;
        private String descripcion;

        public Categoria(){}

        public Categoria(String idCategoria, String nombre, String descripcion) {
            this.idCategoria = idCategoria;
            this.nombre = nombre;
            this.descripcion = descripcion;
        }

        public String getIdCategoria() {
            return idCategoria;
        }

        public void setIdCategoria(String idCategoria) {
            this.idCategoria = idCategoria;
        }

        public String getNombreCategoria() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcionCategoria() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return "Categoria{" +
                    "idCategoria='" + idCategoria + '\'' +
                    ", nombre='" + nombre + '\'' +
                    ", descripcion='" + descripcion + '\'' +
                    '}';
        }
    }
>>>>>>> Stashed changes:Proyecto-Final-p3-main (1)/Proyecto-Final-p3-main/FinalProject/src/main/java/uniquindio/finalproject/Model/Categoria.java
