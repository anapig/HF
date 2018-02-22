import {Component, OnInit} from '@angular/core';
import {GalleryService} from "../service/gallery.service";
import * as _ from 'lodash';
import {Observable} from "rxjs/Observable";

interface PicsResponse {
  images: string[];
  id: string;
}

interface AlbumsResponse {
  name: string;
  id: string;
}


@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent implements OnInit {

  pics$: Observable<PicsResponse[]>;
  albums:AlbumsResponse ;

  constructor(private service: GalleryService) {
  }

  ngOnInit() {
    this.service.getAlbums().subscribe(data => {
      console.log(data);
      this.albums = data ;
    })

  }

  getPhotos(id:string){
   this.pics$ = this.service.getphotos(id).map(data => _.values(data));
  }
}
