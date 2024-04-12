import { MiddlewareConsumer, Module, NestModule } from '@nestjs/common';
import { AuthenModule } from './modules/authen.module';
import { MongoModule } from './modules/mongo.module';
import { KeyTokenModule } from './modules/keytoken.module';
import { UserModule } from './modules/user.module';

@Module({
  imports: [
    MongoModule.forRootAsync(),
    AuthenModule,
    KeyTokenModule,
    UserModule,
  ],
})
export class AppModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {}
}
