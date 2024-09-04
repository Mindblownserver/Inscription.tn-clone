
interface Student{
    cin: string;
    nomFrEtu: string;
    prenomFrEtu: string;
    nomArEtu: string;
    prenomArEtu: string;
    dateNaiss: string;
    nomPere: string;
    prenomPere: string;
    professionPere: string;
    nomMere: string;
    prenomMere: string;
    professionMere: string;
    photoEtu: string;
}

interface Inscription {
    etu: Student;
    au: string;
    fill_etab: FilliereEtablissement;
    paid: number; 
}

interface FilliereEtablissement{
    etab:Etablissement,
    fill:Filliere,
    periode: number,
    prixTotal: number,
    prixTr1: number,
    prixTr2: number
}

interface Filliere{
    idFill: string,
    nomArFill: string,
    nomFrFill: string,
    niveau:number
}

interface Etablissement{
    idEtab: string,
    nomFrEtab: string,
    nomArEtab: string,
    uni: University,
    photoEtab:string
}

interface University{
    idUni: string,
    nomFrUni: string,
    nomArUni: string,
    photoUni: string
}



export {Student, Inscription, FilliereEtablissement, University, Etablissement, Filliere};
 