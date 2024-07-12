import { Component, OnInit } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { authCodeFlowConfig } from '../../auth.config ';
import { Router } from '@angular/router';
import { window } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private oauthService: OAuthService,private router: Router,private cookieService: CookieService) { }

  ngOnInit() {
  }

  logout() {
    console.log("logout");
    // this.oauthService.configure(authCodeFlowConfig);
    // this.oauthService.loadDiscoveryDocumentAndTryLogin();
    this.oauthService.logOut();
    this.router.navigate(['/']); 
    sessionStorage.clear();
    this.cookieService.deleteAll();
  }
  login(){
    this.oauthService.initCodeFlow();
    this.router.navigate(['/']); 
  }
}
