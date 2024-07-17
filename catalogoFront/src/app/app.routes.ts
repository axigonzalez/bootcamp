import { Routes, UrlSegment } from '@angular/router';
import { PageNotFoundComponent } from './main';

export function graficoFiles(url: UrlSegment[]) {
  return url.length === 1 && url[0].path.endsWith('.svg') ? ({consumed: url}) : null;
}
export const routes: Routes = [
  { path: '404.html', component: PageNotFoundComponent },
  { path: '**', component: PageNotFoundComponent },
];
