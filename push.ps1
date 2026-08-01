<#
.SYNOPSIS
    Commits and pushes today's solutions.

.EXAMPLE
    .\push.ps1
    .\push.ps1 -Message "Solve Two Sum and Contains Duplicate"
#>
param(
    [string]$Message = ''
)

$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot

if (-not (git status --porcelain)) {
    Write-Host "Nothing to commit - working tree is clean." -ForegroundColor Yellow
    return
}

git add -A

if (-not $Message) {
    # Name the commit after the problem folders touched in this commit
    $problems = git diff --cached --name-only |
        ForEach-Object { ($_ -split '/')[0] } |
        Where-Object { $_ -match '^\d{4}-' } |
        Select-Object -Unique

    if ($problems) {
        $names = $problems | ForEach-Object {
            $parts = $_ -split '-', 2
            $words = ($parts[1] -split '-') | ForEach-Object {
                $_.Substring(0, 1).ToUpper() + $_.Substring(1)
            }
            "$([int]$parts[0]). $($words -join ' ')"
        }
        $Message = "Solve $($names -join ', ')"
    } else {
        $Message = "Update - $(Get-Date -Format 'yyyy-MM-dd')"
    }
}

git commit -m $Message
if ($?) {
    git push
    if ($?) { Write-Host "`nPushed: $Message" -ForegroundColor Green }
}
