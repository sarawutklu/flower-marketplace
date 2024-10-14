import { Component, OnInit } from '@angular/core';
import { FlowerService } from '../../_services/flower.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-flower',
  templateUrl: './update-flower.component.html',
  styleUrls: ['./update-flower.component.css']
})
export class UpdateFlowerComponent implements OnInit {
  createdBy = 'System';
  flowerForm!: FormGroup;
  imagePreviews: string[] = [];
  images: File[] = [];
  created = false
  constructor(private flowerService: FlowerService,private fb: FormBuilder) { }

  ngOnInit(): void {
    this.flowerForm = this.fb.group({
      name: ['', [Validators.required, Validators.pattern(/\S+/)]], // NotBlank validation
      description: [''],
      price: ['', [Validators.required, Validators.min(0.01)]],
      images:['']
    });
  }


  get name() { return this.flowerForm.get('name'); }
  get price() { return this.flowerForm.get('price'); }

  onSubmit(): void {
    if (this.flowerForm.valid) {
      console.log('Form Submitted!', this.flowerForm.value);
      this.flowerService.createFlower({name: this.flowerForm.get("name")?.value, 
        description: this.flowerForm.get("description")?.value, 
        price: this.flowerForm.get("price")?.value})
        .subscribe(res => {
          console.log(res);
          this.flowerService.uploadFlowerImages(res.data.id, this.images).subscribe((res) => { 
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
    this.flowerForm.reset();
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

