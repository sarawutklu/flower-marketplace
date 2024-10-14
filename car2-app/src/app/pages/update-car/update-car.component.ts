import { Component, OnInit } from '@angular/core';
import { CarService } from '../../_services/car.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrls: ['./update-car.component.css']
})
export class UpdateCarComponent implements OnInit {
  createdBy = 'System';
  carForm!: FormGroup;
  imagePreviews: string[] = [];
  images: File[] = [];
  created = false
  constructor(private carService: CarService,private fb: FormBuilder) { }

  ngOnInit(): void {
    this.carForm = this.fb.group({
      name: ['', [Validators.required, Validators.pattern(/\S+/)]], // NotBlank validation
      description: [''],
      price: ['', [Validators.required, Validators.min(0.01)]],
      make: ['', [Validators.required, Validators.pattern(/\S+/)]], 
      model: ['', [Validators.required, Validators.pattern(/\S+/)]], 
      year: ['', [Validators.required, Validators.pattern(/\S+/)]], 
      color: ['', [Validators.required, Validators.pattern(/\S+/)]], 
      images:['']
    });
  }


  get name() { return this.carForm.get('name'); }
  get price() { return this.carForm.get('price'); }
  get make() { return this.carForm.get('make'); }
  get model() { return this.carForm.get('model'); }
  get year() { return this.carForm.get('year'); }
  get color() { return this.carForm.get('color'); }

  onSubmit(): void {
    if (this.carForm.valid) {
      console.log('Form Submitted!', this.carForm.value);
      this.carService.createCar(
        {
          name: this.carForm.get("name")?.value, 
          description: this.carForm.get("description")?.value, 
          price: this.carForm.get("price")?.value,
          make: this.carForm.get("make")?.value,
          model: this.carForm.get("model")?.value,
          year: this.carForm.get("year")?.value,
          color: this.carForm.get("color")?.value})
        .subscribe(res => {
          console.log(res);
          this.carService.uploadCarImages(res.data.id, this.images).subscribe((res) => { 
            console.log(res);
            this.created =true;
            this.clearForm()
          })
        })
    } else {
      console.log('Form is invalid');
    }
  }

  clearForm(): void {
    this.carForm.reset();
    this.images = [];
    this.imagePreviews = [];
  }

  onImageUpload(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      for (let i = 0; i < input.files.length; i++) {
        const file = input.files[i];
        this.images.push(file);

        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.imagePreviews.push(e.target.result);
        };
        reader.readAsDataURL(file);
      }
    }
  }

  removeImage(index: number): void {
    this.imagePreviews.splice(index, 1);
    this.images.splice(index, 1);
  }
}

