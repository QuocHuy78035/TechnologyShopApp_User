import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { UploadFiles } from 'src/utils/uploadFiles';
import { User } from '../user.model';
import { getSelectData } from 'src/utils';
import { BadRequestException, UnauthorizedException } from '@nestjs/common';
export class UserRepo {
  constructor(@InjectModel('User') private userModel: Model<User>) {}
  async updateUserInfo({ userId, bodyUpdate, file, filter = [] }) {
    const { name, gender, dateOfBirth, address, mobile } = bodyUpdate;
    const avatar = await new UploadFiles(
      'users',
      'image',
      file,
    ).uploadFileAndDownloadURL();
    return await this.userModel
      .findByIdAndUpdate(
        userId,
        { name, gender, dateOfBirth, avatar, address, mobile },
        { new: true },
      )
      .select(getSelectData(filter));
  }

  async updatePassword({
    userId,
    currentPassword,
    newPassword,
    newPasswordConfirm,
  }) {
    const user = await this.userModel.findById(userId).select('+password');
    if (!user.matchPassword(currentPassword)) {
      throw new UnauthorizedException('Incorrect password!');
    }

    if (newPassword !== newPasswordConfirm) {
      throw new BadRequestException(
        'New passwords do not match! Please try again!',
      );
    }

    user.password = newPassword;
    await user.save({ validateBeforeSave: false });
  }
}
