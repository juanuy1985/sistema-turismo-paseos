export interface Cliente {
  id: number;
  tipoDocumento: string;
  numeroDocumento: string;
  direccion: string;
  usuario: {
    id: number;
    nombres: string;
    apellidos: string;
    email: string;
    telefono: string;
    username: string;
    activo: boolean;
    rolId: number;
    rolNombre: string;
  };
}

export interface ClienteRegistro {
  nombres: string;
  apellidos: string;
  email: string;
  telefono: string;
  username: string;
  password: string;
  tipoDocumento: string;
  numeroDocumento: string;
  direccion: string;
}
