import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import 'rxjs/add/operator/map';


interface PicsResponse {
  images: string[];
  id: string;
}
interface AlbumsResponse {
  name: string;
  id: string;
}
@Injectable()
export class GalleryService {

  constructor(private http:HttpClient) { }

  getAlbums(){
    return this.http.get<AlbumsResponse>("http://localhost:8080/albums");
  }

  getphotos(albumId:string){
    return this.http.get<PicsResponse>("http://localhost:8080/photos/"+albumId);
  }
}
