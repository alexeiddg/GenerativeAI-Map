import { Routes } from '@angular/router';
import { HomeComponent } from "./pages/home/home.component";
import { CategoriesComponent } from "./pages/categories/categories.component";
import { MapComponent } from "./pages/map/map.component";
import { ToolsComponent } from "./pages/tools/tools.component";
import { NotFoundComponent } from "./pages/not-found/not-found.component";

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'categories', component: CategoriesComponent },
    { path: 'map', component: MapComponent },
    { path: 'tools', component: ToolsComponent },
    { path: 'not-found', component: NotFoundComponent },
];
