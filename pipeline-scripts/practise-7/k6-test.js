import http from 'k6/http';
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";

export default function () {
    http.get('https://test.k6.io');
}

export function handleSummary(data) {
    return {
          "practise7.html": htmlReport(data),
        };
  }