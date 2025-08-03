# Script de Backup Automatizado

Um script Python para automatizar backups de projetos de desenvolvimento, com organização por data e sincronização opcional com serviços na nuvem.

## Funcionalidades

✅ Backup automático de diretórios  
✅ Compressão em formato ZIP  
✅ Filtragem inteligente de arquivos  
✅ Organização por data  
✅ Upload para Google Drive (simulado)  
✅ Limpeza automática de backups antigos  
✅ Relatório detalhado de operações  
✅ Logging completo  

## Código do Script

```python
#!/usr/bin/env python3
"""
Script de Backup Automatizado
Autor: Desenvolvedor
Data: 2025
Descrição: Automatiza backups de projetos com organização e sincronização
"""

import os
import shutil
import zipfile
import requests
import json
from datetime import datetime, timedelta
from pathlib import Path
import logging
from typing import List, Dict

class BackupManager:
    def __init__(self, config_file: str = "backup_config.json"):
        self.config = self.load_config(config_file)
        self.setup_logging()
        
    def load_config(self, config_file: str) -> Dict:
        """Carrega configurações do arquivo JSON"""
        default_config = {
            "source_dirs": ["./projetos", "./documentos"],
            "backup_dir": "./backups",
            "max_backup_age_days": 30,
            "excluded_patterns": [
                "node_modules", "__pycache__", ".git", 
                "*.log", "*.tmp", ".DS_Store"
            ],
            "cloud_upload": {
                "enabled": True,
                "service": "google_drive",
                "api_endpoint": "https://api.example.com/upload"
            }
        }
        
        if os.path.exists(config_file):
            with open(config_file, 'r', encoding='utf-8') as f:
                config = json.load(f)
                # Merge com configuração padrão
                return {**default_config, **config}
        else:
            # Cria arquivo de configuração padrão
            with open(config_file, 'w', encoding='utf-8') as f:
                json.dump(default_config, f, indent=2, ensure_ascii=False)
            return default_config
    
    def setup_logging(self):
        """Configura sistema de logging"""
        log_dir = Path("logs")
        log_dir.mkdir(exist_ok=True)
        
        logging.basicConfig(
            level=logging.INFO,
            format='%(asctime)s - %(levelname)s - %(message)s',
            handlers=[
                logging.FileHandler(f'logs/backup_{datetime.now().strftime("%Y%m%d")}.log'),
                logging.StreamHandler()
            ]
        )
        self.logger = logging.getLogger(__name__)
    
    def should_exclude(self, file_path: str) -> bool:
        """Verifica se arquivo/diretório deve ser excluído"""
        path_obj = Path(file_path)
        
        for pattern in self.config["excluded_patterns"]:
            if pattern.startswith("*."):
                # Padrão de extensão
                if path_obj.name.endswith(pattern[1:]):
                    return True
            else:
                # Padrão de nome/diretório
                if pattern in path_obj.parts:
                    return True
        return False
    
    def create_backup(self, source_dir: str) -> str:
        """Cria backup comprimido de um diretório"""
        source_path = Path(source_dir)
        if not source_path.exists():
            self.logger.warning(f"Diretório não encontrado: {source_dir}")
            return None
            
        # Prepara diretório de backup
        backup_base = Path(self.config["backup_dir"])
        today = datetime.now().strftime("%Y-%m-%d")
        backup_dir = backup_base / today
        backup_dir.mkdir(parents=True, exist_ok=True)
        
        # Nome do arquivo de backup
        timestamp = datetime.now().strftime("%H%M%S")
        backup_name = f"{source_path.name}_{timestamp}.zip"
        backup_file = backup_dir / backup_name
        
        self.logger.info(f"Criando backup: {source_dir} -> {backup_file}")
        
        # Conta arquivos para relatório
        total_files = 0
        excluded_files = 0
        
        with zipfile.ZipFile(backup_file, 'w', zipfile.ZIP_DEFLATED) as zipf:
            for root, dirs, files in os.walk(source_path):
                # Remove diretórios excluídos da lista para não percorrê-los
                dirs[:] = [d for d in dirs if not self.should_exclude(os.path.join(root, d))]
                
                for file in files:
                    file_path = os.path.join(root, file)
                    total_files += 1
                    
                    if self.should_exclude(file_path):
                        excluded_files += 1
                        continue
                    
                    # Caminho relativo no ZIP
                    arc_path = os.path.relpath(file_path, source_path.parent)
                    zipf.write(file_path, arc_path)
        
        backup_size = backup_file.stat().st_size / (1024 * 1024)  # MB
        self.logger.info(f"Backup criado: {backup_name} ({backup_size:.2f} MB)")
        self.logger.info(f"Arquivos processados: {total_files}, Excluídos: {excluded_files}")
        
        return str(backup_file)
    
    def upload_to_cloud(self, backup_file: str) -> bool:
        """Simula upload para serviço na nuvem"""
        if not self.config["cloud_upload"]["enabled"]:
            return True
            
        self.logger.info(f"Fazendo upload: {backup_file}")
        
        try:
            # Simula chamada para API (substituir por implementação real)
            file_name = os.path.basename(backup_file)
            file_size = os.path.getsize(backup_file)
            
            payload = {
                "filename": file_name,
                "size": file_size,
                "timestamp": datetime.now().isoformat()
            }
            
            # Em um cenário real, faria upload do arquivo
            # response = requests.post(
            #     self.config["cloud_upload"]["api_endpoint"],
            #     files={'file': open(backup_file, 'rb')},
            #     data=payload
            # )
            
            # Simula resposta de sucesso
            self.logger.info(f"Upload concluído: {file_name}")
            return True
            
        except Exception as e:
            self.logger.error(f"Erro no upload: {e}")
            return False
    
    def cleanup_old_backups(self):
        """Remove backups antigos baseado na configuração"""
        backup_base = Path(self.config["backup_dir"])
        if not backup_base.exists():
            return
            
        max_age = timedelta(days=self.config["max_backup_age_days"])
        cutoff_date = datetime.now() - max_age
        
        removed_count = 0
        removed_size = 0
        
        for backup_dir in backup_base.iterdir():
            if backup_dir.is_dir():
                try:
                    # Converte nome do diretório para data
                    dir_date = datetime.strptime(backup_dir.name, "%Y-%m-%d")
                    
                    if dir_date < cutoff_date:
                        # Remove diretório e todos os arquivos
                        for backup_file in backup_dir.iterdir():
                            if backup_file.is_file():
                                removed_size += backup_file.stat().st_size
                                removed_count += 1
                        
                        shutil.rmtree(backup_dir)
                        self.logger.info(f"Removido diretório antigo: {backup_dir}")
                        
                except ValueError:
                    # Nome de diretório não é uma data válida
                    continue
        
        if removed_count > 0:
            removed_size_mb = removed_size / (1024 * 1024)
            self.logger.info(f"Limpeza concluída: {removed_count} arquivos removidos ({removed_size_mb:.2f} MB)")
    
    def generate_report(self, backup_results: List[Dict]) -> str:
        """Gera relatório de backup"""
        report_lines = [
            "=" * 50,
            "RELATÓRIO DE BACKUP",
            "=" * 50,
            f"Data/Hora: {datetime.now().strftime('%d/%m/%Y %H:%M:%S')}",
            f"Total de fontes processadas: {len(backup_results)}",
            ""
        ]
        
        successful_backups = [r for r in backup_results if r['success']]
        failed_backups = [r for r in backup_results if not r['success']]
        
        report_lines.extend([
            f"Backups bem-sucedidos: {len(successful_backups)}",
            f"Backups com falha: {len(failed_backups)}",
            ""
        ])
        
        if successful_backups:
            report_lines.extend(["BACKUPS REALIZADOS:", "-" * 20])
            total_size = 0
            for result in successful_backups:
                if result.get('size'):
                    total_size += result['size']
                    report_lines.append(f"✅ {result['source']} -> {result['backup_file']} ({result['size']:.2f} MB)")
                else:
                    report_lines.append(f"✅ {result['source']} -> {result['backup_file']}")
            
            report_lines.extend(["", f"Tamanho total dos backups: {total_size:.2f} MB", ""])
        
        if failed_backups:
            report_lines.extend(["FALHAS:", "-" * 20])
            for result in failed_backups:
                report_lines.append(f"❌ {result['source']}: {result.get('error', 'Erro desconhecido')}")
            report_lines.append("")
        
        return "\n".join(report_lines)
    
    def run_backup(self):
        """Executa processo completo de backup"""
        self.logger.info("Iniciando processo de backup automatizado")
        
        backup_results = []
        
        # Cria backups para cada diretório fonte
        for source_dir in self.config["source_dirs"]:
            try:
                backup_file = self.create_backup(source_dir)
                
                if backup_file:
                    # Calcula tamanho do backup
                    backup_size = os.path.getsize(backup_file) / (1024 * 1024)
                    
                    # Tenta fazer upload
                    upload_success = self.upload_to_cloud(backup_file)
                    
                    backup_results.append({
                        'source': source_dir,
                        'backup_file': backup_file,
                        'size': backup_size,
                        'uploaded': upload_success,
                        'success': True
                    })
                else:
                    backup_results.append({
                        'source': source_dir,
                        'success': False,
                        'error': 'Falha na criação do backup'
                    })
                    
            except Exception as e:
                self.logger.error(f"Erro ao processar {source_dir}: {e}")
                backup_results.append({
                    'source': source_dir,
                    'success': False,
                    'error': str(e)
                })
        
        # Limpeza de backups antigos
        self.cleanup_old_backups()
        
        # Gera e exibe relatório
        report = self.generate_report(backup_results)
        print(report)
        
        # Salva relatório em arquivo
        report_dir = Path("reports")
        report_dir.mkdir(exist_ok=True)
        report_file = report_dir / f"backup_report_{datetime.now().strftime('%Y%m%d_%H%M%S')}.txt"
        
        with open(report_file, 'w', encoding='utf-8') as f:
            f.write(report)
        
        self.logger.info(f"Processo de backup concluído. Relatório salvo em: {report_file}")

def main():
    """Função principal"""
    backup_manager = BackupManager()
    backup_manager.run_backup()

if __name__ == "__main__":
    main()
```

## Arquivo de Configuração (backup_config.json)

```json
{
  "source_dirs": [
    "./meus_projetos",
    "./documentos_importantes",
    "./codigo_fonte"
  ],
  "backup_dir": "./backups_automaticos",
  "max_backup_age_days": 30,
  "excluded_patterns": [
    "node_modules",
    "__pycache__",
    ".git",
    ".vscode",
    "*.log",
    "*.tmp",
    ".DS_Store",
    "dist",
    "build"
  ],
  "cloud_upload": {
    "enabled": true,
    "service": "google_drive",
    "api_endpoint": "https://api.exemplo.com/upload"
  }
}
```

