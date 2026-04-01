import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Beneficio {
  id?: number;
  nome: string;
  descricao?: string;
  valor: number;
  ativo: boolean;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  beneficios: Beneficio[] = [];
  novo: Beneficio = { nome: '', descricao: '', valor: 0, ativo: true };
  transfer = { fromId: 0, toId: 0, amount: 0 };
  message = '';
  messageType: 'success' | 'error' | '' = '';
  messageTimeout?: any;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.load();
  }

  private apiUrl = 'http://localhost:8080/api/v1/beneficios';

  private showMessage(text: string, type: 'success' | 'error' = 'success') {
    this.message = text;
    this.messageType = type;
    if (this.messageTimeout) {
      clearTimeout(this.messageTimeout);
    }
    this.messageTimeout = setTimeout(() => {
      this.message = '';
      this.messageType = '';
    }, 4500);
  }

  load(): void {
    this.http.get<Beneficio[]>(this.apiUrl).subscribe(data => {
      this.beneficios = data;
    }, err => {
      this.showMessage('Falha ao carregar benefícios: ' + (err.error?.error || err.statusText), 'error');
    });
  }

  save() {
    if (!this.novo.nome || this.novo.valor <= 0) {
      this.showMessage('Preencha nome e valor válidos para criar benefício.', 'error');
      return;
    }
    this.http.post<Beneficio>(this.apiUrl, this.novo).subscribe(() => {
      this.showMessage('Benefício criado com sucesso.', 'success');
      this.novo = { nome: '', descricao: '', valor: 0, ativo: true };
      this.load();
    }, err => {
      this.showMessage('Erro ao criar benefício: ' + (err.error?.error || err.statusText), 'error');
    });
  }

  transferir() {
    if (this.transfer.fromId <= 0 || this.transfer.toId <= 0 || this.transfer.amount <= 0) {
      this.showMessage('Informe valores válidos para transferência.', 'error');
      return;
    }
    this.http.post(this.apiUrl + '/transfer', this.transfer).subscribe(() => {
      this.showMessage('Transferência realizada com sucesso.', 'success');
      this.transfer = { fromId: 0, toId: 0, amount: 0 };
      this.load();
    }, err => {
      this.showMessage('Erro na transferência: ' + (err.error?.error || err.statusText), 'error');
    });
  }

  deleteId(id: number) {
    this.http.delete(this.apiUrl + '/' + id).subscribe(() => {
      this.showMessage('Benefício excluído com sucesso.', 'success');
      this.load();
    }, err => {
      this.showMessage('Erro ao excluir benefício: ' + (err.error?.error || err.statusText), 'error');
    });
  }
}
