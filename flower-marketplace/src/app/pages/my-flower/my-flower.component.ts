import { Component, OnInit } from '@angular/core';
import { FlowerService } from '../../_services/flower.service';
import { AnalyticsService } from '../../_services/analytics.service';
import { OAuthService } from 'angular-oauth2-oidc';
import { authCodeFlowConfig } from '../../auth.config ';

@Component({
  selector: 'app-my-flower',
  templateUrl: './my-flower.component.html',
  styleUrls: ['./my-flower.component.css']
})
export class MyFlowerComponent implements OnInit {
  newFlower = { name: 'flower test 1', description: 'flower test 1', price: 999 };
  flowers: any[] = [];
  imagesPopUp = null
  constructor(
    private flowerService: FlowerService, 
    private analyticsService:AnalyticsService,
    private oauthService:OAuthService
  ) { }

  ngOnInit() {
    if (!this.oauthService.hasValidAccessToken()) {
      this.oauthService.configure(authCodeFlowConfig);
      
      this.oauthService.loadDiscoveryDocumentAndTryLogin().then(() => {
        if (!this.oauthService.hasValidAccessToken()) {
          this.oauthService.initCodeFlow();
          this.getFlowers();
        }
      });
    } 
    this.getFlowers();
    this.analyticsService.trackEvent('myflower', 'myflower loaded into view', 'VIEW_PAGE')
    
  }

  getFlowers(): void {
    this.flowerService.getFlowers().subscribe(
      data => {
      this.flowers = data.data;
      console.log(this.flowers);
    },
    err => this.getFlowers(),
  );
  }

  handleImagesPopUp(item: any): void { 
    this.imagesPopUp = item?.images;
  }
}
