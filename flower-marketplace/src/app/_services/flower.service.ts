import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FlowerService {

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getFlowers(): Observable<any> {
    return this.http.get(`${this.apiUrl}/flowers`);
  }

  createFlower(flower: any): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post(`${this.apiUrl}/flowers`, flower, { headers });
  }

  updateFlower(id: number, flower: any): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.put(`${this.apiUrl}/flowers/${id}`, flower, { headers });
  }

  deleteFlower(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/flowers/${id}`);
  }

  uploadFlowerImages(flowerId: number, images: File[]): Observable<any> {
    const formData: FormData = new FormData();
    images.forEach(image => formData.append('images', image));
    return this.http.post(`${this.apiUrl}/flowers/${flowerId}/images`, formData);
  }

  getFlowerImage(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/flowers/image/${id}`, { responseType: 'blob' });
  }
}
