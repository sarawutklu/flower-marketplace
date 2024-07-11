import { Component, OnInit } from '@angular/core';
import { FlowerService } from '../../_services/flower.service';

@Component({
  selector: 'app-my-flower',
  templateUrl: './my-flower.component.html',
  styleUrls: ['./my-flower.component.css']
})
export class MyFlowerComponent implements OnInit {
  newFlower = { name: 'flower test 1', description: 'flower test 1', price: 999 };
  flowers: any[] = [];
  imagesPopUp = null
  constructor(private flowerService: FlowerService) { }

  ngOnInit() {
    this.getFlowers();
  }

  getFlowers(): void {
    this.flowerService.getFlowers("System").subscribe(data => {
      this.flowers = data.data;
      console.log(this.flowers);
    });
  }

  handleImagesPopUp(item: any): void { 
    this.imagesPopUp = item?.images;
  }
}
