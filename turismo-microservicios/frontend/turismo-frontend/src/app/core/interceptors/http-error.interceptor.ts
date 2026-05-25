import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, throwError } from 'rxjs';

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const snackBar = inject(MatSnackBar);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let mensaje = 'Ocurrió un error inesperado.';

      if (error.error && typeof error.error === 'object' && 'message' in error.error) {
        mensaje = String(error.error.message);
      } else if (typeof error.error === 'string' && error.error.trim().length > 0) {
        mensaje = error.error;
      } else if (error.message) {
        mensaje = error.message;
      }

      snackBar.open(`Error HTTP: ${mensaje}`, 'Cerrar', {
        duration: 5000,
        horizontalPosition: 'right',
        verticalPosition: 'top'
      });

      return throwError(() => error);
    })
  );
};
