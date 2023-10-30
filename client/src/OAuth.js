const CLIENT_ID = "c79e9307cc2008c5f47c1712cfc8257d";
const REDIRECT_URI = "http://localhost:3000/plonit/auth/kakao";

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
