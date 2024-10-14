import { Component, OnInit } from '@angular/core';
import { CarService } from '../../_services/car.service';
import { AnalyticsService } from '../../_services/analytics.service';
import { OAuthService } from 'angular-oauth2-oidc';
import { authCodeFlowConfig } from '../../auth.config ';

@Component({
  selector: 'app-my-car',
  templateUrl: './my-car.component.html',
  styleUrls: ['./my-car.component.css']
})
export class MyCarComponent implements OnInit {
  newFlower = { name: 'flower test 1', description: 'flower test 1', price: 999 };
  flowers: any[] = [];
  imagesPopUp = null
  constructor(
    private flowerService: CarService, 
    private analyticsService:AnalyticsService,
    private oauthService:OAuthService
  ) { }

  ngOnInit() {
    if (!this.oauthService.hasValidAccessToken()) {
      this.oauthService.configure(authCodeFlowConfig);
      
      this.oauthService.loadDiscoveryDocumentAndTryLogin().then(() => {
        if (!this.oauthService.hasValidAccessToken()) {
          this.oauthService.initCodeFlow();
          this.getCars();
        }
      });
    } 
    this.getCars();
    this.analyticsService.trackEvent('myflower', 'myflower loaded into view', 'VIEW_PAGE')
    
  }

  getCars(): void {
    this.flowerService.getCars().subscribe(
      data => {
      this.flowers = data.data;
      console.log(this.flowers);
    },
    err => this.getCars(),
  );
  }

  handleImagesPopUp(item: any): void { 
    this.imagesPopUp = item?.images;
  }
}
