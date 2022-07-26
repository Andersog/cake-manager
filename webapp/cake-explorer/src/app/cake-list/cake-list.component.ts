import { Component, Input } from '@angular/core';
import { Cake, CakeRequest, CakeService } from './cake-list.service';

@Component({
  selector: 'CakeList',
  templateUrl: './cake-list.component.html',
  styleUrls: ['./cake-list.component.less'],
})
export class CakeListComponent {

  @Input()
  public cakes?: Cake[];

  constructor(private cakeService: CakeService) {
  }
}
