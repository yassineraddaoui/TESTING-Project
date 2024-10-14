import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'io.ionic.starter',
  appName: 'book-network-mobile',
  webDir: 'www',
  server: {
    cleartext: true,
    allowNavigation: ['http://10.0.2.2:8080'], 
    androidScheme: 'http'
  },
  android: {
    allowMixedContent: true,
  }
 
};

export default config;
