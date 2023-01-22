import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaptureWorkoutComponent } from './capture-workout.component';

describe('CaptureWorkoutComponent', () => {
  let component: CaptureWorkoutComponent;
  let fixture: ComponentFixture<CaptureWorkoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CaptureWorkoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CaptureWorkoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
