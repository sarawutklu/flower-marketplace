import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getCars(): Observable<any> {
    return this.http.get(`${this.apiUrl}/cars`);
  }

  createCar(flower: any): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post(`${this.apiUrl}/cars`, flower, { headers });
  }

  updateCar(id: number, flower: any): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.put(`${this.apiUrl}/cars/${id}`, flower, { headers });
  }

  deleteCar(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/cars/${id}`);
  }

  uploadCarImages(flowerId: number, images: File[]): Observable<any> {
    const formData: FormData = new FormData();
    images.forEach(image => formData.append('images', image));
    return this.http.post(`${this.apiUrl}/cars/${flowerId}/images`, formData);
  }

  getCarImage(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/cars/image/${id}`, { responseType: 'blob' });
  }
}
