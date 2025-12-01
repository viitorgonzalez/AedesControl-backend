INSERT INTO addresses (
    street, number, complement, neighborhood_id,
    zip_code, reference_point, status, latitude, longitude
)
VALUES
-- São Paulo
('Rua Direita', '120', NULL, 1, '01002-900', 'Próx. à Catedral da Sé', 'LIVRE', -23.5509, -46.6345),
('Rua dos Pinheiros', '820', 'Apto 15', 2, '05422-001', 'Padaria Esquina', 'SUSPEITA', -23.5674, -46.6893),

-- Campinas
('Rua Sampainho', '55', NULL, 3, '13024-120', 'Em frente ao parque', 'CONFIRMADO', -22.9062, -47.0482),
('Av. Norte-Sul', '2000', NULL, 4, '13076-201', 'Posto Shell', 'LIVRE', -22.8850, -47.0588),

-- Rio de Janeiro
('Av Atlântica', '3500', 'Cobertura', 5, '22070-001', 'Perto do posto 5', 'SUSPEITA', -22.9730, -43.1869),
('Rua da Assembleia', '10', NULL, 6, '20011-000', 'Tribunal de Justiça', 'CONFIRMADO', -22.9086, -43.1764),

-- Belo Horizonte
('Rua Pernambuco', '800', NULL, 7, '30130-151', 'Shopping Pátio Savassi', 'LIVRE', -19.9380, -43.9330),
('Av Antônio Carlos', '5000', NULL, 8, '31270-901', 'UFMG', 'SUSPEITA', -19.8705, -43.9699);
