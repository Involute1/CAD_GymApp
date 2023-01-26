import { Component } from '@angular/core';
import { AuthService } from '../../../../shared/services/auth.service';
import { Gym, GymService } from '../../../../shared/services/gym.service';
import { Observable, of, tap } from 'rxjs';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent {
  gym$: Observable<Gym> = of();
  uploadedImage: File = {} as File;
  image: any;

  constructor(public authService: AuthService, private gymService: GymService) {
    if (this.authService.hasRole(['GymOwner'])) {
      this.gym$ = this.gymService
        .getGym(this.authService.userData.tenantId)
        .pipe(tap((gym) => console.log(gym)));
    }
  }

  public onImageUpload(event: any) {
    this.uploadedImage = event.target.files[0];
  }

  imageUploadAction() {
    const imageFormData = new FormData();
    imageFormData.append('image', this.uploadedImage, this.uploadedImage.name);
    this.gymService.uploadGymLogo(
      imageFormData,
      this.authService.userData.tenantId
    );
  }
}
