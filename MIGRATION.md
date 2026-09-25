# Migration from SVN

## Source

| Item | Value |
|---|---|
| SVN URL | svn://erp.projectsrl.net/svn/alibow/moduli_rev_10_7-2026 |
| Revision | r2389 |
| Migration date | 2026-09-25 |

## Method

Snapshot import **without history**: `svn export` of the local working copy
(externals included as plain files), copied into a clone of this repository
on top of the initial commit.

## Not migrated

- **SVN history** (commits, authors, branches, tags): still available on the SVN server.
- **svn:externals**: content imported as regular files; no link to the external sources is kept.
- **svn:ignore**: replaced by `.gitignore`.
- **Empty directories**: not tracked by Git.
- `src/main/webapp/css/dafne.css.orig`: excluded by `.gitignore` (merge leftover).

## Line endings

`.gitattributes` normalizes text files to LF in the repository (`* text=auto`);
Office, image, PDF, PSD and DWG files are marked as binary.

## Post-migration checklist

- [ ] Set the SVN path read-only
- [ ] Repoint Jenkins jobs from SVN to Git
- [ ] Decide whether `src/resources/docs` (binary project documentation) stays in the repository
