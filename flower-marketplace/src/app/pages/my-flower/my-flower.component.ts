import { Component, OnInit } from '@angular/core';
import { FlowerService } from '../../_services/flower.service';
import { AnalyticsService } from '../../_services/analytics.service';

@Component({
  selector: 'app-my-flower',
  templateUrl: './my-flower.component.html',
  styleUrls: ['./my-flower.component.css']
})
export class MyFlowerComponent implements OnInit {
  newFlower = { name: 'flower test 1', description: 'flower test 1', price: 999 };
  flowers: any[] = [];
  imagesPopUp = null
  constructor(private flowerService: FlowerService, private analyticsService:AnalyticsService) { }

  ngOnInit() {
    this.analyticsService.trackEvent('myflower', 'myflower loaded into view', 'VIEW_PAGE')
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
